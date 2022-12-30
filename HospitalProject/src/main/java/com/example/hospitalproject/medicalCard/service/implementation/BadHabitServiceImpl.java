package com.example.hospitalproject.medicalCard.service.implementation;

import com.example.hospitalproject.medicalCard.exception.IllegalBadHabitException;
import com.example.hospitalproject.medicalCard.exception.MedicalCardNotFoundException;
import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.repository.BadHabitRepository;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import com.example.hospitalproject.medicalCard.service.BadHabitService;
import com.mongodb.Function;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class BadHabitServiceImpl implements BadHabitService {
    private final MedicalCardRepository repository;
    private final BadHabitRepository badHabitRepository;

    private static final Function<String, String> f = (s) -> s.trim().replaceAll(" +", " ").toLowerCase();
    private static final int MINIMUM_BAD_HABIT_LENGTH = 1;

    @Override
    public Set<String> getAll(String id) {
        if (repository.existsById(id)) {
            return badHabitRepository.getBadHabits(id);
        }
        throw new MedicalCardNotFoundException();
    }

    @Override
    public Set<String> add(String id, String badHabit) {
        badHabit = formatAndCheckBadHabit(badHabit);
        Optional<MedicalCard> cardOptional = repository.findById(id);
        if (cardOptional.isPresent()) {
            MedicalCard card = cardOptional.get();
            Set<String> badHabits = card.getBadHabits();
            if (badHabits.contains(badHabit)) {
                throw new IllegalBadHabitException("There is already present " + badHabit + " habit");
            }
            badHabitRepository.addBadHabit(id, badHabit);
            return badHabitRepository.getBadHabits(id);
        }
        throw new MedicalCardNotFoundException();
    }

    @Override
    public Set<String> delete(String id, String badHabit) {
        final String badHabit1 = formatAndCheckBadHabit(badHabit);
        return modifyBadHabit(id, badHabit1, () -> badHabitRepository.deleteBadHabit(id, badHabit1));
    }

    @Override
    public Set<String> update(String id, String oldBadHabit, String newBadHabit) {
        final String newBadHabit1 = formatAndCheckBadHabit(newBadHabit);
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

    private String formatAndCheckBadHabit(String badHabit) {
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
