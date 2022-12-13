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
            MedicalCard card = cardOptional.get();
            record.setDate(LocalDateTime.now());
            repository.addMedicalRecord(card.getId(), record);
            return repository.findById(id).get().getRecords();
        }
        throw new MedicalCardNotFoundException();
    }

    static final int HOURS_FOR_UPDATE = 24;

    public List<MedicalRecord> updateMedicalRecord(String id, MedicalRecord newRecord) {
        LocalDateTime createdDate = newRecord.getDate();
        if (createdDate == null) {
            throw new IllegalArgumentException("Date of updating record is not specified");
        }
        if (ChronoUnit.HOURS.between(createdDate, LocalDateTime.now()) >= HOURS_FOR_UPDATE) {
            String message = "You can't update medical records after " + HOURS_FOR_UPDATE + " hours";
            throw new UnsupportedOperationException(message);
        }
        String doctor = newRecord.getDoctor();
        if (doctor == null || doctor.length() < DOCTOR_NAME_MINIMUM_LENGTH) {
            throw new IllegalDoctorException("Value of Doctor = " + doctor + " is illegal");
        }
        Optional<MedicalCard> cardOptional = repository.findById(id);
        if (cardOptional.isPresent()) {
            repository.getMedicalRecord(id, createdDate)
                    .orElseThrow(() -> {
                        String message = "There is no record at " + createdDate + " in " + id + " card";
                        throw new MedicalRecordNotFoundException(message);
                    });
            newRecord.setEdited(LocalDateTime.now());
            repository.updateMedicalRecord(id, newRecord);
            return repository.findById(id).get().getRecords();
        }
        throw new MedicalCardNotFoundException();
    }

}
