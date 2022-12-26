package com.example.hospitalproject.medicalCard.repository.implementation;

import com.example.hospitalproject.medicalCard.model.Allergy;
import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.repository.AllergyRepository;
import com.example.hospitalproject.medicalCard.repository.ArrayRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@AllArgsConstructor
public class AllergyRepositoryImpl implements AllergyRepository {

    private final ArrayRepository arrayRepository;


    private static final String allergies = "allergies";

    public enum AllergyFields {
        title,
        reaction;

        public final String path;
        public final String path$;

        AllergyFields() {
            this.path = allergies + "." + this.name();
            this.path$ = allergies + ".$" + this.name();
        }
    }

    @Override
    public Set<Allergy> getAllAllergiesById(String id) {
        return arrayRepository.getArrayFromCardById(id, MedicalCard.field.allergies,
                Set.of(), MedicalCard::getAllergies);
    }

    @Override
    public void addAllergy(String id, Allergy allergy) {
        String array = MedicalCard.field.allergies.name();
        arrayRepository.pushArrayElement(id, array, allergy);
    }

    @Override
    public void updateAllergy(String id, String title, Allergy allergy) {
        String where = AllergyFields.title.path;
        String index = MedicalCard.field.allergies.nameDot$;
        arrayRepository.updateArrayElement(id, where, title, index, allergy);
    }

    @Override
    public void deleteAllergy(String id, String title) {
        String array = MedicalCard.field.allergies.n;
        String elementField = AllergyFields.title.name();
        arrayRepository.deleteArrayElement(id, array, elementField, title);
    }
}
