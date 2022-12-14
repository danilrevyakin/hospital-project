package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.Allergy;

import java.util.Set;

public interface AllergyRepository {

    Set<Allergy> getAllAllergiesById(String id);

    void addAllergy(String id, Allergy allergy);

    void updateAllergy(String id, String title, Allergy allergy);

    void deleteAllergy(String id, String title);
}
