package com.example.hospitalproject.medicalCard.service;

import com.example.hospitalproject.medicalCard.exception.IllegalBadHabitException;
import com.example.hospitalproject.medicalCard.exception.MedicalCardNotFoundException;
import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.repository.BadHabitRepository;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import com.mongodb.Function;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class BadHabitService {
    private final MedicalCardRepository repository;
    private final BadHabitRepository badHabitRepository;

    private static final Function<String, String> f = (s) -> s.trim().replaceAll(" +", " ").toLowerCase();
    private static final int MINIMUM_BAD_HABIT_LENGTH = 1;

    public Set<String> getAllBadHabits(String id) {
        if (repository.existsById(id)) {
            return badHabitRepository.getBadHabits(id);
        }
        throw new MedicalCardNotFoundException();
    }

    public Set<String> addBadHabit(String id, String badHabit) {
        badHabit = formatBadHabit(badHabit);
        Optional<MedicalCard> cardOptional = repository.findById(id);
        if (cardOptional.isPresent()) {
            MedicalCard card = cardOptional.get();
            Set<String> badHabits = card.getBadHabits();
            if (badHabits != null && badHabits.contains(badHabit)) {
                throw new IllegalBadHabitException("There is already present " + badHabit + " habit");
            }
            badHabitRepository.addBadHabit(id, badHabit);
            return badHabitRepository.getBadHabits(id);
        }
        throw new MedicalCardNotFoundException();
    }

    public Set<String> deleteBadHabit(String id, String badHabit) {
        final String badHabit1 = formatBadHabit(badHabit);
        return modifyBadHabit(id, badHabit1, () -> badHabitRepository.deleteBadHabit(id, badHabit1));
    }

    public Set<String> updateBadHabit(String id, String oldBadHabit, String newBadHabit) {
        final String newBadHabit1 = formatBadHabit(newBadHabit);
        return modifyBadHabit(id, oldBadHabit, () -> badHabitRepository.updateBadHabit(id, oldBadHabit, newBadHabit1));
    }

    private Set<String> modifyBadHabit(String id, String badHabit, Runnable runner) {
        Optional<MedicalCard> cardOptional = repository.findById(id);
        if (cardOptional.isPresent()) {
            MedicalCard card = cardOptional.get();
            if (!card.getBadHabits().contains(badHabit)) {
                throw new IllegalBadHabitException("There is no " + badHabit + " habit");
            }
            runner.run();
            return badHabitRepository.getBadHabits(id);
        }
        throw new MedicalCardNotFoundException();
    }

    private String formatBadHabit(String badHabit) {
        if (badHabit == null) {
            throw new IllegalBadHabitException("Bad habit can not be null");
        }
        badHabit = f.apply(badHabit);
        if (badHabit.length() < MINIMUM_BAD_HABIT_LENGTH) {
            throw new IllegalBadHabitException("Bad habit can not be null");
        }
        return badHabit;
    }
}
