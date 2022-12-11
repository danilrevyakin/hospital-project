package com.example.hospitalproject.medicalCard.service;

import com.example.hospitalproject.medicalCard.exception.MedicalCardNotFoundException;
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

    public List<MedicalRecord> addMedicalRecord(String key, MedicalRecord record) {
        Optional<MedicalCard> cardOptional = repository.findById(key);
        if (cardOptional.isPresent()) {
            MedicalCard card = cardOptional.get();
            record.setDate(LocalDateTime.now());
            repository.addMedicalRecord(card.getId(), record);
            return repository.findById(key).get().getRecords();
        }
        throw new MedicalCardNotFoundException();
    }
}
