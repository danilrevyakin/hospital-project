package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.logging.Logger;

import static com.example.hospitalproject.medicalCard.repository.CustomMedicalCardRepositoryImpl.getQueryById;

@Repository
@AllArgsConstructor
public class BadHabitRepositoryImpl implements BadHabitRepository {

    private final MongoTemplate template;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void addBadHabit(String id, String badHabit) {
        Query query = getQueryById(id);
        Update update = new Update().addToSet(MedicalCard.field.badHabits.name(), badHabit);
        template.updateFirst(query, update, MedicalCard.class);
    }

    @Override
    public void deleteBadHabit(String id, String badHabit) {
        Query query = getQueryById(id);
        Update update = new Update().pull(MedicalCard.field.badHabits.name(), badHabit);
        logger.info(update.toString());
        UpdateResult updateResult = template.updateFirst(query, update, MedicalCard.class);
        logger.info(updateResult.toString());
    }
}
