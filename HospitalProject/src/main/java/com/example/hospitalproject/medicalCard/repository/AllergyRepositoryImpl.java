package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.Allergy;
import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.mongodb.BasicDBObject;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import static com.example.hospitalproject.medicalCard.repository.CustomMedicalCardRepositoryImpl.getQueryById;

@Repository
@AllArgsConstructor
public class AllergyRepositoryImpl implements AllergyRepository {

    private final MongoTemplate template;

    @Override
    public void addAllergy(String id, Allergy allergy) {
        Query query = getQueryById(id);
        Update update = new Update().addToSet(MedicalCard.field.allergies.name(), allergy);
        template.updateFirst(query, update, MedicalCard.class);
    }

    @Override
    public void updateAllergy(String id, String title, Allergy allergy) {
        Query query = getQueryById(id).
                addCriteria(Criteria.where(Allergy.field.title.path).is(title));
        Update update = new Update().set(MedicalCard.field.allergies.name() + ".$", allergy);
        template.updateFirst(query, update, MedicalCard.class);
    }

    @Override
    public void deleteAllergy(String id, String title) {
        String titlePath = Allergy.field.title.path;
        Query query = getQueryById(id).
                addCriteria(Criteria.where(titlePath).is(title));
        BasicDBObject basicDBObject = new BasicDBObject(titlePath, title);
        Update update = new Update().pull(titlePath, basicDBObject);
        template.updateFirst(query, update, MedicalCard.class);
    }
}
