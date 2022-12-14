package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.mongodb.Function;

import java.util.Collection;

public interface ArrayRepository {
    <E extends Collection<?>> E getArrayFromCardById(String id,
                                                     MedicalCard.field field,
                                                     E empty,
                                                     Function<MedicalCard, E> getter);

    <T> void deleteArrayElementFromCard(String id, String array,
                                        String elementField, T fieldValue);

    <T, V> void updateArrayElement(String id, String elementFieldPath, T elementField,
                                          String index, V newValue);
}
