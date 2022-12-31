package com.example.hospitalproject.medicalCard.unit.repository;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.repository.BadHabitRepository;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static com.example.hospitalproject.medicalCard.unit.repository.AllergyRepositoryTest.getField;

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
        Set<String> actual = habitRepository.getBadHabits(id);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getBadHabitsTest() {
        //given
        final MedicalCard card = new MedicalCard(
                id,
                null,
                Set.of("habit", "smoke", "sugar"),
                null,
                null
        );
        repository.save(card);
        //when
        final Set<String> expected = getBadHabits();
        Set<String> actual = habitRepository.getBadHabits(id);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    private Set<String> getBadHabits(){
        return getField(repository.findById(id), MedicalCard::getBadHabits);
    }

    @Test
    void deleteBadHabit() {
        //given
        final String habit = "habit";
        final Set<String> habits = Set.of(habit, "smoke");
        final MedicalCard card = new MedicalCard(
                id,
                null,
                habits,
                null,
                null
        );
        repository.save(card);
        final Set<String> expected = Set.of("smoke");
        //when
        habitRepository.deleteBadHabit(id, habit);
        Set<String> actual = habitRepository.getBadHabits(id);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void updateBadHabit() {
        //given
        final String oldValue = "old";
        final String newValue = "new";
        final Set<String> habits = Set.of(oldValue);
        final MedicalCard card = new MedicalCard(
                id,
                null,
                habits,
                null,
                null
        );
        repository.save(card);
        final Set<String> expected = Set.of(newValue);
        //when
        habitRepository.updateBadHabit(id, oldValue, newValue);
        Set<String> actual = getBadHabits();
        //then
        assertThat(actual).isEqualTo(expected);
    }

}