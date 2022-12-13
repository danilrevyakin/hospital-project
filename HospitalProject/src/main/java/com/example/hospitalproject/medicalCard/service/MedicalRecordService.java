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

    public List<MedicalRecord> updateMedicalRecord(String id, LocalDateTime dateTime, MedicalRecord newRecord) {
        String doctor = newRecord.getDoctor();
        if (doctor == null || doctor.length() < DOCTOR_NAME_MINIMUM_LENGTH) {
            throw new IllegalDoctorException("Value of Doctor = " + doctor + " is illegal");
        }
        Optional<MedicalCard> cardOptional = repository.findById(id);
        if (cardOptional.isPresent()) {
            repository.getMedicalRecord(id, dateTime)
                    .ifPresentOrElse((x) -> newRecord.setDate(x.getDate()), () -> {
                        String message = "There is no record in " + dateTime + " in " + id + " card";
                        throw new MedicalRecordNotFoundException(message);});
            newRecord.setEdited(LocalDateTime.now());
            repository.updateMedicalRecord(id, dateTime, newRecord);
            return repository.findById(id).get().getRecords();
        }
        throw new MedicalCardNotFoundException();
    }

    public List<MedicalRecord> updateMedicalRecord(String id, String date, MedicalRecord newRecord) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        return updateMedicalRecord(id, dateTime, newRecord);
    }
}
