package com.example.hospitalproject.medicalCard;

import com.example.hospitalproject.medicalCard.exception.MedicalCardNotFoundException;
import com.example.hospitalproject.medicalCard.model.Allergy;
import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public List<String> addBadHabit(String key, String badHabit) {
        Optional<MedicalCard> cardOptional = repository.findById(key);
        if (cardOptional.isPresent()) {
            MedicalCard card = cardOptional.get();
            repository.addBadHabit(card.getId(), badHabit);
            return repository.findById(key).get().getBadHabits();
        }
        throw new MedicalCardNotFoundException();
    }

    public List<Allergy> addAllergy(String key, Allergy allergy) {
        Optional<MedicalCard> cardOptional = repository.findById(key);
        if (cardOptional.isPresent()) {
            MedicalCard card = cardOptional.get();
            repository.addAllergy(card.getId(), allergy);
            return repository.findById(key).get().getAllergies();
        }
        throw new MedicalCardNotFoundException();
    }

    public List<Allergy> addAllergy(String key, String title, String reaction) {
        return addAllergy(key, new Allergy(title, reaction));
    }

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

    public List<MedicalRecord> addMedicalRecord(String key, String info, String symptoms, String treatment, String doctor) {
        MedicalRecord record = new MedicalRecord(info, symptoms, treatment, doctor, null, null);
        return addMedicalRecord(key, record);
    }
}
