package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.mongodb.Function;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import static com.example.hospitalproject.medicalCard.repository.MedicalRecordRepositoryImpl.getQueryById;

@AllArgsConstructor
@Repository
public class ArrayRepositoryImpl implements ArrayRepository{

    private final MongoTemplate template;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
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
}
