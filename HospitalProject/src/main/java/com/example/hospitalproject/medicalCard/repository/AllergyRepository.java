package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.Allergy;

public interface AllergyRepository {
    void addAllergy(String id, Allergy allergy);

    void updateAllergy(String id, String title, Allergy allergy);

    void deleteAllergy(String id, String title);
}
