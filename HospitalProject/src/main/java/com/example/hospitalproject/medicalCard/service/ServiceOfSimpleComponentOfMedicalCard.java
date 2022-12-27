package com.example.hospitalproject.medicalCard.service;

import java.util.Collection;

public interface ServiceOfSimpleComponentOfMedicalCard<T, E extends Collection<T>, K>{
    E getAll(String id);
    E add(String id, T value);
    E delete(String id, K key);
    E update(String id, K key, T newValue);
}
