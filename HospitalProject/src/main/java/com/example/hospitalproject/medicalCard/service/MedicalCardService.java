package com.example.hospitalproject.medicalCard.service;

import com.example.hospitalproject.medicalCard.exception.MedicalCardNotFoundException;
import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
        repository.findById(id).ifPresent((x) -> {
            String message = "Medical card with this id is already present";
            throw new IllegalStateException(message);
        });
        MedicalCard card = new MedicalCard(id, Set.of(), Set.of(), List.of(), LocalDate.now());
        repository.save(card);
        return card;
    }

    public List<MedicalCard> deleteCard(String id) {
        repository.findById(id)
                .ifPresentOrElse(card -> repository.deleteById(id),
                () -> {throw new MedicalCardNotFoundException();});
        return repository.findAll();
    }
}
