package com.example.hospitalproject.medicalCard.service;

import com.example.hospitalproject.medicalCard.exception.MedicalCardNotFoundException;
import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class MedicalCardService {

    private final MedicalCardRepository repository;

    public List<MedicalCard> getAllCards() {
        return repository.findAll();
    }

    public MedicalCard getMedicalCardById(String id) {
        return repository.findById(id).orElseThrow(MedicalCardNotFoundException::new);
    }

    public MedicalCard createEmptyMedicalCard(String id) {
        String message = "Medical card with this id is already present";
        repository.findById(id).ifPresent((x) -> {
            throw new IllegalStateException(message);
        });
        MedicalCard card = new MedicalCard(id, null, null, null, LocalDate.now());
        repository.save(card);
        return card;
    }
}
