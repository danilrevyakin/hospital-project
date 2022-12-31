package com.example.hospitalproject.medicalCard.unit.service.implementation;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.repository.BadHabitRepository;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import com.example.hospitalproject.medicalCard.service.implementation.BadHabitServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(MockitoExtension.class)
class BadHabitServiceImplTest {

    @Mock private BadHabitRepository habitRepository;
    @Mock private MedicalCardRepository repository;
    private BadHabitServiceImpl service;
    private static final String id = "testID";

    @BeforeEach
    void setUp() {
        service = new BadHabitServiceImpl(repository, habitRepository);
    }

    @Test
    void testGetAll() {
        //given
        given(repository.existsById(id))
                .willReturn(true);
        //when
        service.getAll(id);
        //then
        verify(habitRepository).getBadHabits(id);
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
        final String habit = "smoke";
        given(repository.findById(id))
                .willReturn(Optional.of(card));
        //when
        service.add(id, habit);
        //then
        ArgumentCaptor<String> habitArgument =
                ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> idArg =
                ArgumentCaptor.forClass(String.class);
        verify(habitRepository).addBadHabit(idArg.capture(), habitArgument.capture());
        var capturedHabit = habitArgument.getValue();
        assertThat(capturedHabit).isEqualTo(habit);
    }

    @Test
    void testDelete() {
        //given
        final String habit = "smoke";
        final MedicalCard card = new MedicalCard(
                id,
                null,
                Set.of(habit),
                null,
                null
        );
        given(repository.findById(id))
                .willReturn(Optional.of(card));
        //when
        service.delete(id, habit);
        //then
        ArgumentCaptor<String> habitArgument =
                ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> idArg =
                ArgumentCaptor.forClass(String.class);
        verify(habitRepository).deleteBadHabit(idArg.capture(), habitArgument.capture());
        var capturedHabit = habitArgument.getValue();
        assertThat(capturedHabit).isEqualTo(habit);
    }

    @Test
    void testUpdate() {
        //given
        final String old = "smoke";
        final String updated = "sugar";
        final MedicalCard card = new MedicalCard(
                id,
                null,
                Set.of(old),
                null,
                null
        );
        given(repository.findById(id))
                .willReturn(Optional.of(card));
        //when
        service.update(id, old, updated);
        //then
        ArgumentCaptor<String> oldArgument =
                ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> updatedArg =
                ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> idArg =
                ArgumentCaptor.forClass(String.class);
        verify(habitRepository).updateBadHabit(idArg.capture(),
                oldArgument.capture(), updatedArg.capture());
        var updatedArgValue = updatedArg.getValue();
        assertThat(updatedArgValue).isEqualTo(updated);
        var oldValue = oldArgument.getValue();
        assertThat(oldValue).isEqualTo(old);
    }
}