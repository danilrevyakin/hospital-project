package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.model.Allergy;
import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;


@Repository
@AllArgsConstructor
public class CustomMedicalCardRepositoryImpl implements CustomMedicalCardRepository {

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
        Update update = new Update().set(MedicalCard.field.allergies.name(), allergy);
        template.updateFirst(query, update, MedicalCard.class);
    }

    @Override
    public void deleteAllergy(String id, String title, Allergy allergy) {

    }

    @Override
    public void addBadHabit(String id, String badHabit) {
        Query query = getQueryById(id);
        Update update = new Update().addToSet(MedicalCard.field.badHabits.name(), badHabit);
        template.updateFirst(query, update, MedicalCard.class);
    }

    @Override
    public void deleteBadHabit(String id, String badHabit) {

    }

    @Override
    public void addMedicalRecord(String id, MedicalRecord record) {
        Query query = getQueryById(id);
        Update update = new Update().push(MedicalCard.field.records.name(), record);
        template.updateFirst(query, update, MedicalCard.class);
    }

    @Override
    public void updateMedicalRecord(String id, MedicalRecord oldRecord, MedicalRecord newRecord) {

    }

    @Override
    public void deleteMedicalRecord(String id, MedicalRecord record) {

    }

    private Query getQueryById(String id) {
        return new Query().addCriteria(Criteria.where(MedicalCard.field.id.name()).is(id));
    }
}
