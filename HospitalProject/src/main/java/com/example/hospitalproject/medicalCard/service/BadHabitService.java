package com.example.hospitalproject.medicalCard.service;

import com.example.hospitalproject.medicalCard.exception.MedicalCardNotFoundException;
import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class BadHabitService {
    private final MedicalCardRepository repository;

    public Set<String> getAllBadHabits(String id){
        if(repository.existsById(id)){
            return repository.getBadHabitsById(id);
        }
        throw new MedicalCardNotFoundException();
    }

    public Set<String> addBadHabit(String id, String badHabit) {
        Optional<MedicalCard> cardOptional = repository.findById(id);
        if (cardOptional.isPresent()) {
            MedicalCard card = cardOptional.get();
            Set<String> badHabits = card.getBadHabits();
            if (badHabits != null && badHabits.contains(badHabit)) {
                throw new IllegalStateException("There is already present " + badHabit + " habit");
            }
            repository.addBadHabit(id, badHabit);
            return repository.getBadHabitsById(id);
        }
        throw new MedicalCardNotFoundException();
    }

    public Set<String> deleteBadHabit(String id, String badHabit) {
        Optional<MedicalCard> cardOptional = repository.findById(id);
        if (cardOptional.isPresent()) {
            MedicalCard card = cardOptional.get();
            if (!card.getBadHabits().contains(badHabit)) {
                throw new IllegalStateException("There is no " + badHabit + " habit");
            }
            repository.deleteBadHabit(id, badHabit);
            return repository.findById(id).get().getBadHabits();
        }
        throw new MedicalCardNotFoundException();
    }
}
