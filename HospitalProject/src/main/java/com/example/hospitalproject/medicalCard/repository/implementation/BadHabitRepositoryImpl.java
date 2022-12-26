package com.example.hospitalproject.medicalCard.repository.implementation;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.repository.ArrayRepository;
import com.example.hospitalproject.medicalCard.repository.BadHabitRepository;
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
public class BadHabitRepositoryImpl implements BadHabitRepository {

    private final MongoTemplate template;
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final ArrayRepository arrayRepository;

    @Override
    public Set<String> getBadHabits(String id) {
        return arrayRepository.getArrayFromCardById(id, MedicalCard.field.badHabits,
                Set.of(), MedicalCard::getBadHabits);
    }

    @Override
    public void addBadHabit(String id, String badHabit) {
        String array = MedicalCard.field.badHabits.name();
        arrayRepository.pushArrayElement(id, array, badHabit);
    }

    @Override
    public void deleteBadHabit(String id, String badHabit) {
        Query query = getQueryById(id);
        logger.info(query.toString());
        Update update = new Update().pull(MedicalCard.field.badHabits.name(), badHabit);
        logger.info(update.toString());
        UpdateResult updateResult = template.updateFirst(query, update, MedicalCard.class);
        logger.info(updateResult.toString());
    }

    @Override
    public void updateBadHabit(String id, String oldHabit, String newHabit) {
        arrayRepository.updateArrayElement(id, MedicalCard.field.badHabits.name(),
                oldHabit, MedicalCard.field.badHabits.nameDot$, newHabit);
    }
}
