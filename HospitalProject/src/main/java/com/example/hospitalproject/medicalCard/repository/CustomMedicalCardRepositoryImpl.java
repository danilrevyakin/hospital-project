package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.MedicalCard;
import com.example.hospitalproject.medicalCard.model.Allergy;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CustomMedicalCardRepositoryImpl implements CustomMedicalCardRepository{

    private final MongoTemplate template;

    @Override
    public void addAllergy(String id, Allergy allergy) {
        Query query = new Query().addCriteria(Criteria.where(MedicalCard.field.id.name()).is(id));
        Update update = new Update().addToSet(MedicalCard.field.allergies.name(), allergy);
        template.updateFirst(query, update, MedicalCard.class);
    }
}
