package com.example.hospitalproject.medicalCard;

import com.example.hospitalproject.medicalCard.model.Allergy;
import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class MedicalCardService {

    private final MedicalCardRepository repository;

    public List<MedicalCard> getAllCards() {
        return repository.findAll();
    }

    public MedicalCard getMedicalCardByKey(String key) {//get saved or create new
        return repository.findById(key).orElseGet(() -> {
            MedicalCard card = new MedicalCard(key, null, null, null, LocalDate.now());
            repository.save(card);
            return card;
        });
    }

    public void addBadHabit(String key, String badHabit) {
        repository.findById(key)
                .ifPresent((card) -> repository.addBadHabit(card.getId(), badHabit));
    }

    public void addAllergy(String key, Allergy allergy) {
        repository.findById(key)
                .ifPresent((card) -> repository.addAllergy(card.getId(), allergy));
    }

    public void addAllergy(String key, String title, String reaction) {
        addAllergy(key, new Allergy(title, reaction));
    }

    public void addMedicalRecord(String key, MedicalRecord record) {
        repository.findById(key)
                .ifPresent((card -> repository
                        .addMedicalRecord(card.getId(), record)));
    }

    public void addMedicalRecord(String key, String info, String symptoms, String treatment, String doctor) {
        MedicalRecord record = new MedicalRecord(info, symptoms, treatment, doctor, LocalDateTime.now(), null);
        addMedicalRecord(key, record);
    }
}
