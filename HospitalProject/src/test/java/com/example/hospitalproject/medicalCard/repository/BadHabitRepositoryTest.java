package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BadHabitRepositoryTest {

    @Autowired
    private BadHabitRepository habitRepository;
    @Autowired
    private MedicalCardRepository repository;
    private final String id = "testID";

    @AfterEach
    void deleteCard() {
        repository.deleteById(id);
    }

    @Test
    void addBadHabit() {
        //given
        final MedicalCard card = new MedicalCard(
                id,
                null,
                null,
                null,
                null
        );
        final String habit = "smoke";
        var expected = Set.of(habit);
        repository.save(card);
        //when
        habitRepository.addBadHabit(id, habit);
        //then
        habitRepository.getBadHabits(id);
        //then
        assertThat(repository.existsMedicalCardByIdAndBadHabitsContaining(id, expected)).isTrue();
    }

    @Test
    void getBadHabits() {
        //given
        final Set<String> expected = Set.of("habit", "smoke", "sugar");
        final MedicalCard card = new MedicalCard(
                id,
                null,
                expected,
                null,
                null
        );
        repository.save(card);
        //when
        Set<String> actual = habitRepository.getBadHabits(id);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void deleteBadHabit() {
        //given
        final String habit = "habit";
        final Set<String> habits = Set.of(habit, "smoke", "sugar");
        final MedicalCard card = new MedicalCard(
                id,
                null,
                habits,
                null,
                null
        );
        repository.save(card);
        final Set<String> expected = Set.of("smoke", "sugar");
        //when
        habitRepository.deleteBadHabit(id, habit);
        //then
        assertThat(repository.existsMedicalCardByIdAndBadHabitsContaining(id, expected)).isTrue();
    }

    @Test
    void updateBadHabit() {
        //given
        final String oldValue = "old";
        final String newValue = "new";
        final Set<String> habits = Set.of(oldValue, "smoke", "sugar");
        final MedicalCard card = new MedicalCard(
                id,
                null,
                habits,
                null,
                null
        );
        repository.save(card);
        final Set<String> expected = Set.of(newValue, "smoke", "sugar");
        //when
        habitRepository.updateBadHabit(id, oldValue, newValue);
        habitRepository.getBadHabits(id);
        //then
        assertThat(repository.existsMedicalCardByIdAndBadHabitsContaining(id, expected)).isTrue();
    }
}