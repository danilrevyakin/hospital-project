package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.MedicalRecord;

public interface MedicalRecordRepository {

    void addMedicalRecord(String id, MedicalRecord record);

    void updateMedicalRecord(String id, MedicalRecord oldRecord, MedicalRecord newRecord);

    void deleteMedicalRecord(String id, MedicalRecord record);
}
