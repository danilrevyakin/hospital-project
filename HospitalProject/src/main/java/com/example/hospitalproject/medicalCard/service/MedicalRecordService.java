package com.example.hospitalproject.medicalCard.service;

import com.example.hospitalproject.medicalCard.exception.IllegalDoctorException;
import com.example.hospitalproject.medicalCard.exception.MedicalCardNotFoundException;
import com.example.hospitalproject.medicalCard.exception.MedicalRecordNotFoundException;
import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@AllArgsConstructor
@Service
public class MedicalRecordService {

    private final MedicalCardRepository repository;

    private static final int DOCTOR_NAME_MINIMUM_LENGTH = 2;

    public List<MedicalRecord> addMedicalRecord(String id, MedicalRecord record) {
        String doctor = record.getDoctor();
        if (doctor == null || doctor.length() < DOCTOR_NAME_MINIMUM_LENGTH) {
            throw new IllegalDoctorException("Value of Doctor = " + doctor + " is illegal");
        }
        if (repository.existsById(id)) {
            record.setDate(LocalDateTime.now());
            repository.addMedicalRecord(id, record);
            return repository.getMedicalRecordsById(id);
        }
        throw new MedicalCardNotFoundException();
    }

    public MedicalRecord getMedicalRecord(String id, LocalDateTime date) {
        if (repository.existsById(id)) {
            List<MedicalRecord> records = repository.getMedicalRecordsByIdAndDate(id, date);
            if (records.size() == 0) {
                String message = "There is no record at " + date + " in " + id + " card";
                throw new MedicalRecordNotFoundException(message);
            }
            return records.get(0);
        }
        throw new MedicalCardNotFoundException();
    }

    public List<MedicalRecord> getAllMedicalRecordsById(String id) {
        if (repository.existsById(id)) {
            return repository.getMedicalRecordsById(id);
        }
        throw new MedicalCardNotFoundException();
    }

    static final int HOURS_FOR_UPDATE = 24;

    public List<MedicalRecord> updateMedicalRecord(String id, String doctor, MedicalRecord newRecord) {
        LocalDateTime dateOfCreating = newRecord.getDate();
        modifyingRecordValidation(id, dateOfCreating, doctor);
        newRecord.setEdited(LocalDateTime.now());
        repository.updateMedicalRecord(id, dateOfCreating, newRecord);
        return repository.getMedicalRecordsById(id);
    }

    public List<MedicalRecord> deleteMedicalRecord(String id, LocalDateTime dateOfCreating, String doctor) {
        modifyingRecordValidation(id, dateOfCreating, doctor);
        repository.deleteMedicalRecord(id, dateOfCreating);
        return repository.getMedicalRecordsById(id);
    }

    private void modifyingRecordValidation(String id, LocalDateTime dateOfCreating, String doctor) {
        if (dateOfCreating == null) {
            throw new IllegalArgumentException("Date of updating record is not specified");
        }
        if (ChronoUnit.HOURS.between(dateOfCreating, LocalDateTime.now()) >= HOURS_FOR_UPDATE) {
            String message = "You can't modify medical records after " + HOURS_FOR_UPDATE + " hours";
            throw new UnsupportedOperationException(message);
        }
        if (doctor == null || doctor.length() < DOCTOR_NAME_MINIMUM_LENGTH) {
            throw new IllegalDoctorException("Value of Doctor = " + doctor + " is illegal");
        }
        MedicalRecord oldRecord = getMedicalRecord(id, dateOfCreating);
        if (!oldRecord.getDoctor().equals(doctor)) {
            String message = "Value of Doctor = " + doctor + " is illegal." +
                    " Only creator can can modify his own record.";
            throw new IllegalDoctorException(message);
        }
    }

}
