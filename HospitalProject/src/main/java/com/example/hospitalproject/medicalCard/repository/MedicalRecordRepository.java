package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.MedicalRecord;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MedicalRecordRepository {

    Optional<MedicalRecord> getMedicalRecord(String id, LocalDateTime dateOfOldRecord);

    void addMedicalRecord(String id, MedicalRecord record);

    void updateMedicalRecord(String id, MedicalRecord newRecord);

    void deleteMedicalRecord(String id, LocalDateTime dateOfOldRecord);
}
