package com.example.hospitalproject.medicalCard.service;

import com.example.hospitalproject.medicalCard.exception.IllegalDoctorException;
import com.example.hospitalproject.medicalCard.exception.MedicalCardNotFoundException;
import com.example.hospitalproject.medicalCard.exception.MedicalRecordNotFoundException;
import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

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
        Optional<MedicalCard> cardOptional = repository.findById(id);
        if (cardOptional.isPresent()) {
            record.setDate(LocalDateTime.now());
            repository.addMedicalRecord(id, record);
            return repository.findById(id).get().getRecords();
        }
        throw new MedicalCardNotFoundException();
    }

    public MedicalRecord getMedicalRecord(String id, LocalDateTime date){
        Optional<MedicalCard> cardOptional = repository.findById(id);
        if (cardOptional.isPresent()) {
            List<MedicalRecord> records = repository.getMedicalRecordsByIdAndDate(id, date);
            if(records.size() == 0){
                String message = "There is no record at " + date + " in " + id + " card";
                throw new MedicalRecordNotFoundException(message);
            }
            if(records.size() > 1){
                String message = "Medical card can't contain more than one record in some point of time";
                throw new IllegalStateException(message);
            }
            return records.get(0);
        }
        throw new MedicalCardNotFoundException();
    }

    static final int HOURS_FOR_UPDATE = 24;

    public List<MedicalRecord> updateMedicalRecord(String id, MedicalRecord newRecord) {
        LocalDateTime dateOfCreating = newRecord.getDate();
        if (dateOfCreating == null) {
            throw new IllegalArgumentException("Date of updating record is not specified");
        }
        if (ChronoUnit.HOURS.between(dateOfCreating, LocalDateTime.now()) >= HOURS_FOR_UPDATE) {
            String message = "You can't update medical records after " + HOURS_FOR_UPDATE + " hours";
            throw new UnsupportedOperationException(message);
        }
        String doctor = newRecord.getDoctor();
        if (doctor == null || doctor.length() < DOCTOR_NAME_MINIMUM_LENGTH) {
            throw new IllegalDoctorException("Value of Doctor = " + doctor + " is illegal");
        }
        MedicalRecord oldRecord = getMedicalRecord(id, dateOfCreating);
        if(!oldRecord.getDoctor().equals(doctor)){
            String message = "Value of Doctor = " + doctor + " is illegal." +
                    " Only creator can can modify his own record.";
            throw new IllegalDoctorException(message);
        }
        newRecord.setEdited(LocalDateTime.now());
        repository.updateMedicalRecord(id, dateOfCreating, newRecord);
        return repository.findById(id).get().getRecords();
    }

}
