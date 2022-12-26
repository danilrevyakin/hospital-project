package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.mongodb.Function;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collection;

public interface ArrayRepository {
    <E extends Collection<?>> E getArrayFromCardById(String id,
                                                     MedicalCard.field field,
                                                     E empty,
                                                     Function<MedicalCard, E> getter);

    <E extends Collection<?>> E getArrayFromCardByQuery(Query query,
                                                     MedicalCard.field field,
                                                     E empty,
                                                     Function<MedicalCard, E> getter);

    <E extends Collection<?>> E getArrayFromCardByQuery(Query query,
                                                        String field,
                                                        E empty,
                                                        Function<MedicalCard, E> getter);

    <V> void pushArrayElement(String id, String where, V newValue);

    <T> void deleteArrayElement(String id, String array,
                                String elementField, T fieldValue);

    <T, V> void updateArrayElement(String id, String where, T is,
                                          String index, V newValue);
}
