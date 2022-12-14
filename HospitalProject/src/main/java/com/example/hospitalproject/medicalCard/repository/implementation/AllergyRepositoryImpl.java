package com.example.hospitalproject.medicalCard.repository.implementation;

import com.example.hospitalproject.medicalCard.model.Allergy;
import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.repository.AllergyRepository;
import com.example.hospitalproject.medicalCard.repository.ArrayRepository;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.logging.Logger;

import static com.example.hospitalproject.medicalCard.repository.implementation.MedicalRecordRepositoryImpl.getQueryById;

@Repository
@AllArgsConstructor
public class AllergyRepositoryImpl implements AllergyRepository {

    private final MongoTemplate template;
    private final Logger logger = Logger.getLogger(this.getClass().getName());
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
        return arrayRepository.getArrayFromCardById(id, MedicalCard.field.allergies, Set.of(), MedicalCard::getAllergies);
    }

    @Override
    public void addAllergy(String id, Allergy allergy) {
        Query query = getQueryById(id);
        logger.info(query.toString());
        Update update = new Update().addToSet(MedicalCard.field.allergies.name(), allergy);
        logger.info(update.toString());
        UpdateResult updateResult = template.updateFirst(query, update, MedicalCard.class);
        logger.info(updateResult.toString());
    }

    @Override
    public void updateAllergy(String id, String title, Allergy allergy) {
        String elementFieldPath = AllergyFields.title.path;
        String index = MedicalCard.field.allergies.nameDot$;
        arrayRepository.updateArrayElement(id, elementFieldPath, title, index, allergy);
    }

    //db.medicalCard.updateOne({_id: "id"},{$pull: {allergies:{title: "title"}}})
    @Override
    public void deleteAllergy(String id, String title) {
        arrayRepository.deleteArrayElementFromCard(id,MedicalCard.field.allergies.n, AllergyFields.title.name(), title);
    }
}
