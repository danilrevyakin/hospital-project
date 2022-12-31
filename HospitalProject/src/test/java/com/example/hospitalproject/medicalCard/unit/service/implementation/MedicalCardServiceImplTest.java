package com.example.hospitalproject.medicalCard.unit.service.implementation;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import com.example.hospitalproject.medicalCard.service.implementation.MedicalCardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MedicalCardServiceImplTest {
    @Mock
    private MedicalCardRepository repository;
    private MedicalCardServiceImpl service;
    private static final String id = "testID";

    @BeforeEach
    void setUp() {
        service = new MedicalCardServiceImpl(repository, Clock.systemUTC());
    }

    @Test
    void getAllCards() {
        //given
        given(repository.findAll())
                .willReturn(List.of());
//        when
        service.getAllCards();
//        then
        verify(repository).findAll();
    }

    @Test
    void getMedicalCardById() {
        //given
        given(repository.findById(id))
                .willReturn(Optional.of(new MedicalCard()));
//        when
        service.getMedicalCardById(id);
        //then
        verify(repository).findById(id);
    }

    @Test
    void createEmptyMedicalCard() {
        //given
        given(repository.findById(id))
                .willReturn(Optional.empty());
//        when
        service.createEmptyMedicalCard(id);
        //then
        verify(repository).save(any());
    }

    @Test
    void deleteCard() {
        //given
        given(repository.findById(id))
                .willReturn(Optional.of(new MedicalCard()));
//        when
        service.deleteCard(id);
        //then
        verify(repository).deleteById(id);
    }
}