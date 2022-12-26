package com.example.hospitalproject.medicalCard.service;

import com.example.hospitalproject.medicalCard.exception.IllegalDoctorException;
import com.example.hospitalproject.medicalCard.exception.IllegalMedicalRecordException;
import com.example.hospitalproject.medicalCard.exception.MedicalCardNotFoundException;
import com.example.hospitalproject.medicalCard.exception.MedicalRecordNotFoundException;
import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import com.mongodb.Function;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@AllArgsConstructor
@Service
public class MedicalRecordService {

    private final MedicalCardRepository repository;
    private static final Function<String, String> f = (s) -> s.trim().replaceAll(" +", " ");

    private static final int DOCTOR_NAME_MINIMUM_LENGTH = 2;
    private static final int RECORD_INFO_MINIMUM_LENGTH = 2;

    public List<MedicalRecord> addMedicalRecord(String id, String doctor, MedicalRecord record) {
        if (doctor == null) {
            throw new IllegalDoctorException("You have not specified doctor parameter!");
        }
        doctor = f.apply(doctor);
        if (doctor.length() < DOCTOR_NAME_MINIMUM_LENGTH) {
            throw new IllegalDoctorException("Value of Doctor = " + doctor + " is illegal");
        }
        if (repository.existsById(id)) {
            formatRecordData(record);
            record.setDoctor(doctor);
            record.setDate(LocalDateTime.now());
            repository.addMedicalRecord(id, record);
            return repository.getMedicalRecordsById(id);
        }
        throw new MedicalCardNotFoundException();
    }

    private void formatRecordData(MedicalRecord record) {
        String info = record.getInfo();
        if (info == null) {
            throw new IllegalMedicalRecordException("Info of record can not be null");
        }
        info = f.apply(info);
        if (info.length() < RECORD_INFO_MINIMUM_LENGTH) {
            String message = "Length of record info can not be less than " + RECORD_INFO_MINIMUM_LENGTH;
            throw new IllegalMedicalRecordException(message);
        }
        record.setInfo(info);
        String symptoms = record.getSymptoms();
        if (symptoms != null) {
            record.setSymptoms(f.apply(symptoms));
        }
        String treatment = record.getTreatment();
        if (treatment != null) {
            record.setTreatment(f.apply(treatment));
        }
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
        formatRecordData(newRecord);
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
