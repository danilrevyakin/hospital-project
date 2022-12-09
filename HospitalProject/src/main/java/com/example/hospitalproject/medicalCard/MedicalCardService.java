package com.example.hospitalproject.medicalCard;

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

    public MedicalCard getMedicalCardByKey(Long key) {//get saved or create new
        return repository.findMedicalCardBySqlKey(key).orElseGet(()->{
            MedicalCard card = new MedicalCard(key, null, null, null, LocalDate.now());
            repository.save(card);
            return card;
        });
    }
}
