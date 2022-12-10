package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.Allergy;
import com.example.hospitalproject.medicalCard.model.MedicalRecord;

public interface CustomMedicalCardRepository {
    void addAllergy(String id, Allergy allergy);
    void addBadHabit(String id, String badHabit);
    void addMedicalRecord(String id, MedicalRecord record);
}
