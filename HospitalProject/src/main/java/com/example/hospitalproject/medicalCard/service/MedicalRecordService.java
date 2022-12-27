package com.example.hospitalproject.medicalCard.service;

import com.example.hospitalproject.medicalCard.model.MedicalRecord;

import java.time.LocalDateTime;
import java.util.List;

public interface MedicalRecordService {
    List<MedicalRecord> addMedicalRecord(String id, MedicalRecord record);
    MedicalRecord getMedicalRecord(String id, LocalDateTime date);
    List<MedicalRecord> getAllMedicalRecordsById(String id);
    List<MedicalRecord> updateMedicalRecord(String id, String doctor, MedicalRecord newRecord);
    List<MedicalRecord> deleteMedicalRecord(String id, LocalDateTime dateOfCreating, String doctor);
}
