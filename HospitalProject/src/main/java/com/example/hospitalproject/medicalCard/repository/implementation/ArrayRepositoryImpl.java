package com.example.hospitalproject.medicalCard.repository.implementation;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.repository.ArrayRepository;
import com.mongodb.Function;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import static com.example.hospitalproject.medicalCard.repository.implementation.MedicalRecordRepositoryImpl.getQueryById;

@AllArgsConstructor
@Repository
public class ArrayRepositoryImpl implements ArrayRepository {

    private final MongoTemplate template;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public <E extends Collection<?>> E getArrayFromCardById(String id, MedicalCard.field field,
                                                            E empty, Function<MedicalCard, E> getter) {
        Query query = getQueryById(id);
        return getArrayFromCardByQuery(query, field, empty, getter);
    }

    @Override
    public <E extends Collection<?>> E getArrayFromCardByQuery(Query query, MedicalCard.field field,
                                                               E empty, Function<MedicalCard, E> getter) {
        return getArrayFromCardByQuery(query, field.name(), empty, getter);
    }

    @Override
    public <E extends Collection<?>> E getArrayFromCardByQuery(Query query, String field,
                                                               E empty, Function<MedicalCard, E> getter) {
        query.fields().include(field)
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
    public <T> void deleteArrayElement(String id, String array, String where, T is) {
        Query query = getQueryById(id);
        logger.info(query.toString());
        Update update = new Update().pull(array,
                Query.query(Criteria.where(where).is(is)));
        logger.info(update.toString());
        UpdateResult updateResult = template.updateFirst(query, update, MedicalCard.class);
        logger.info(updateResult.toString());
    }

    @Override
    public <T, V> void updateArrayElement(String id, String where, T is,
                                          String index, V newValue) {
        Query query = getQueryById(id)
                .addCriteria(Criteria.where(where).is(is));
        logger.info(query.toString());
        Update update = new Update().set(index, newValue);
        logger.info(update.toString());
        UpdateResult updateResult = template.updateFirst(query, update, MedicalCard.class);
        logger.info(updateResult.toString());
    }

    @Override
    public <V> void pushArrayElement(String id, String where, V newValue) {
        Query query = getQueryById(id);
        logger.info(query.toString());
        Update update = new Update().push(where, newValue);
        logger.info(update.toString());
        UpdateResult updateResult = template.updateFirst(query, update, MedicalCard.class);
        logger.info(updateResult.toString());
    }
}
