package com.example.hospitalproject.medicalCard.service.implementation;

import com.example.hospitalproject.medicalCard.exception.MedicalCardNotFoundException;
import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import com.example.hospitalproject.medicalCard.service.MedicalCardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class MedicalCardServiceImpl implements MedicalCardService {

    private final MedicalCardRepository repository;
    private final Clock clock;

    @Override
    public List<MedicalCard> getAllCards() {
        return repository.findAll();
    }

    @Override
    public List<MedicalCard> deleteAllCards() {
        repository.deleteAll();
        return repository.findAll();
    }

    @Override
    public MedicalCard getMedicalCardById(String id) {
        return repository.findById(id).orElseThrow(MedicalCardNotFoundException::new);
    }

    @Override
    public MedicalCard createEmptyMedicalCard(String id) {
        repository.findById(id).ifPresent((x) -> {
            String message = "Medical card with this id is already present";
            throw new IllegalStateException(message);
        });
        MedicalCard card = new MedicalCard(id, Set.of(), Set.of(), List.of(), LocalDate.now(clock));
        repository.save(card);
        return card;
    }

    @Override
    public List<MedicalCard> deleteCard(String id) {
        repository.findById(id)
                .ifPresentOrElse(card -> repository.deleteById(id),
                () -> {throw new MedicalCardNotFoundException();});
        return repository.findAll();
    }
}
