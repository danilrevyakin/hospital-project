package com.example.hospitalproject.medicalCard.integration;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.repository.BadHabitRepository;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import com.example.hospitalproject.medicalCard.service.implementation.BadHabitServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BadHabitServiceIntegrationTest {

    @Autowired
    private BadHabitRepository habitRepository;
    @Autowired
    private MedicalCardRepository repository;
    @Autowired
    private BadHabitServiceImpl service;
    private static final String id = "testID";

    @AfterEach
    void deleteCard() {
        repository.deleteById(id);
    }

    @Test
    void testAdd() {
        //given
        final MedicalCard card = new MedicalCard(
                id,
                null,
                Set.of(),
                null,
                null
        );
        final String habit1 = "     sMoke                 ";
        final String expected1 = "smoke";
        final String habit2 = "     DRInk    bEer                 ";
        final String expected2 = "drink beer";
        var expected = Set.of(expected1, expected2);
        repository.save(card);
        //when
        service.add(id, habit1);
        service.add(id, habit2);
        //then
        Set<String> badHabits = habitRepository.getBadHabits(id);
        assertThat(badHabits.equals(expected)).isTrue();
    }

    @Test
    void testDelete() {
        //given
        final String habit = "     sMokeS   SIGARS                ";
        final String expected1 = "smokes sigars";
        final String expected2 = "drink beer";
        var expected = Set.of(expected2);
        final MedicalCard card = new MedicalCard(
                id,
                null,
                Set.of(expected1, expected2),
                null,
                null
        );
        repository.save(card);
        //when
        service.delete(id, habit);
        //then
        Set<String> badHabits = habitRepository.getBadHabits(id);
        assertThat(badHabits.equals(expected)).isTrue();
    }

    @Test
    void testUpdate() {
        //given
        final String old = "smoke";
        final String updated = "       suGar       ";
        final String expected = "sugar";
        final MedicalCard card = new MedicalCard(
                id,
                null,
                Set.of(old),
                null,
                null
        );
        repository.save(card);
        //when
        service.update(id, old, updated);
        //then
        Set<String> badHabits = habitRepository.getBadHabits(id);
        assertThat(badHabits.equals(Set.of(expected))).isTrue();
    }
}