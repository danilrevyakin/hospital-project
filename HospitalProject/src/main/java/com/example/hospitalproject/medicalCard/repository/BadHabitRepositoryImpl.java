package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.mongodb.Function;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static com.example.hospitalproject.medicalCard.repository.MedicalRecordRepositoryImpl.getQueryById;

@Repository
@AllArgsConstructor
public class BadHabitRepositoryImpl implements BadHabitRepository {

    private final MongoTemplate template;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public Set<String> getBadHabitsById(String id) {
//        Query query = getQueryById(id);
//        query.fields().include(MedicalCard.field.badHabits.name())
//                .exclude(MedicalCard.field.id.n);
//        logger.info(query.toString());
//        List<MedicalCard> objects = template.find(query, MedicalCard.class);
//        logger.info(objects.toString());
//        if (objects.size() < 1) {
//            return Set.of();
//        } else if (objects.get(0).getBadHabits() == null || objects.get(0).getBadHabits().size() < 1) {
//            return Set.of();
//        }
//        return objects.get(0).getBadHabits();
        return getArrayFromCardById(id, MedicalCard.field.badHabits, Set.of(), MedicalCard::getBadHabits);
    }

    public <E extends Collection<?>> E getArrayFromCardById(String id,
                                                            MedicalCard.field field,
                                                            E empty,
                                                            Function<MedicalCard, E> getter) {
        Query query = getQueryById(id);
        query.fields().include(field.name())
                .exclude(MedicalCard.field.id.n);
        logger.info(query.toString());
        List<MedicalCard> objects = template.find(query, MedicalCard.class);
        logger.info(objects.toString());
        if (objects.size() < 1) {
            return empty;
        }
        E collection = getter.apply(objects.get(0));
        if (collection == null || collection.size() < 1) {
            return empty;
        }
        return collection;
    }

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
