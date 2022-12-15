package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.MedicalRecord;

import java.time.LocalDateTime;
import java.util.List;

public interface MedicalRecordRepository {

    List<MedicalRecord> getMedicalRecordsByIdAndDate(String id, LocalDateTime dateOfOldRecord);

    List<MedicalRecord> getMedicalRecordsById(String id);

    void addMedicalRecord(String id, MedicalRecord record);

    void updateMedicalRecord(String id,LocalDateTime date, MedicalRecord newRecord);

    void deleteMedicalRecord(String id, LocalDateTime dateOfOldRecord);
}
