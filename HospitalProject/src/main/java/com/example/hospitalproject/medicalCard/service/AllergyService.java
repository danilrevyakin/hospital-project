package com.example.hospitalproject.medicalCard.service;

import com.example.hospitalproject.medicalCard.exception.MedicalCardNotFoundException;
import com.example.hospitalproject.medicalCard.model.Allergy;
import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class AllergyService {

    private final MedicalCardRepository repository;

    public Set<Allergy> addAllergy(String id, Allergy allergy) {
        Optional<MedicalCard> cardOptional = repository.findById(id);
        if (cardOptional.isPresent()) {
            if(cardOptional.get().getAllergies().contains(allergy)){
                throw new IllegalStateException("There is already present " + allergy);
            }
            repository.addAllergy(id, allergy);
            return repository.findById(id).get().getAllergies();
        }
        throw new MedicalCardNotFoundException();
    }

    public Set<Allergy> updateAllergy(String id, String title, Allergy allergy) {
        Optional<MedicalCard> cardOptional = repository.findById(id);
        if (cardOptional.isPresent()) {
            if(!cardOptional.get().getAllergies().contains(new Allergy(title, null))){
                throw new IllegalStateException("There is no " + title + " in " + id + " card");
            }
            repository.updateAllergy(id, title, allergy);
            return repository.findById(id).get().getAllergies();
        }
        throw new MedicalCardNotFoundException();
    }

    public Set<Allergy> deleteAllergy(String id, String title) {
        Optional<MedicalCard> cardOptional = repository.findById(id);
        if (cardOptional.isPresent()) {
            if(!cardOptional.get().getAllergies().contains(new Allergy(title, null))){
                throw new IllegalStateException("There is no " + title + " in " + id + " card");
            }
            repository.deleteAllergy(id, title);
            return repository.findById(id).get().getAllergies();
        }
        throw new MedicalCardNotFoundException();
    }
}
