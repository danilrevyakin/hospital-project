package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.mongodb.Function;

import java.util.Collection;

public class ArrayRepositoryImpl implements ArrayRepository{
    @Override
    public <E extends Collection<?>> E getArrayFromCardById(String id, MedicalCard.field field, E empty, Function<MedicalCard, E> getter) {
        return null;
    }
}
