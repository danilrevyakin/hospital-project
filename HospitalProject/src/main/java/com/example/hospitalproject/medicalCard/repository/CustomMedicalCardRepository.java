package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.MedicalRecord;

public interface CustomMedicalCardRepository {

    void addBadHabit(String id, String badHabit);

    void deleteBadHabit(String id, String badHabit);

    void addMedicalRecord(String id, MedicalRecord record);

    void updateMedicalRecord(String id, MedicalRecord oldRecord, MedicalRecord newRecord);

    void deleteMedicalRecord(String id, MedicalRecord record);
}
