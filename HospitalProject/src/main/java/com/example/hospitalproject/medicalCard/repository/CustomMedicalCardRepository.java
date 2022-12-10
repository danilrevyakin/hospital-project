package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.Allergy;

public interface CustomMedicalCardRepository {
    void addAllergy(String id, Allergy allergy);
}
