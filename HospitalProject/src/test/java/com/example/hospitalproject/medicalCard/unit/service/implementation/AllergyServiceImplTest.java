package com.example.hospitalproject.medicalCard.unit.service.implementation;

import com.example.hospitalproject.medicalCard.model.Allergy;
import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.repository.AllergyRepository;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import com.example.hospitalproject.medicalCard.service.implementation.AllergyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AllergyServiceImplTest {

    @Mock
    private AllergyRepository allergyRepository;
    @Mock
    private MedicalCardRepository repository;
    private AllergyServiceImpl service;
    private static final String id = "testID";

    @BeforeEach
    void setUp() {
        service = new AllergyServiceImpl(repository, allergyRepository);
    }

    @Test
    void getAll() {
        //given
        given(repository.existsById(id))
                .willReturn(true);
        //when
        service.getAll(id);
        //then
        verify(allergyRepository).getAllAllergiesById(id);
    }

    @Test
    void add() {
        //given
        Allergy allergy = new Allergy(
                "honey",
                "dead"
        );
        Allergy allergy2 = new Allergy(
                "apple",
                "dead"
        );
        final MedicalCard card = new MedicalCard(
                id,
                Set.of(allergy),
                null,
                null,
                null
        );
        given(repository.findById(id))
                .willReturn(Optional.of(card));
        //when
        service.add(id, allergy2);
        //then
        var added =
                ArgumentCaptor.forClass(Allergy.class);
        ArgumentCaptor<String> idArg =
                ArgumentCaptor.forClass(String.class);
        verify(allergyRepository).addAllergy(idArg.capture(), added.capture());
        var capturedAllergy = added.getValue();
        assertThat(capturedAllergy).isEqualTo(allergy2);
    }

    @Test
    void update() {
        //given
        final var old = new Allergy(
                "honey",
                "dead"
        );
        final var updated = new Allergy(
                "apple",
                "dead"
        );
        final MedicalCard card = new MedicalCard(
                id,
                Set.of(old),
                null,
                null,
                null
        );
        given(repository.findById(id))
                .willReturn(Optional.of(card));
        //when
        service.update(id, old.getTitle(), updated);
        //then
        var oldArgument =
                ArgumentCaptor.forClass(String.class);
        var updatedArg =
                ArgumentCaptor.forClass(Allergy.class);
        ArgumentCaptor<String> idArg =
                ArgumentCaptor.forClass(String.class);
        verify(allergyRepository).updateAllergy(idArg.capture(),
                oldArgument.capture(), updatedArg.capture());
        var updatedArgValue = updatedArg.getValue();
        assertThat(updatedArgValue).isEqualTo(updated);
        var oldValue = oldArgument.getValue();
        assertThat(oldValue).isEqualTo(old.getTitle());
    }

    @Test
    void delete() {
        //given
        final var old = new Allergy(
                "honey",
                "dead"
        );
        final var deleted = new Allergy(
                "apple",
                "dead"
        );
        final MedicalCard card = new MedicalCard(
                id,
                Set.of(old, deleted),
                null,
                null,
                null
        );
        given(repository.findById(id))
                .willReturn(Optional.of(card));
        //when
        service.delete(id, deleted.getTitle());
        //then
        ArgumentCaptor<String> deleteArg =
                ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> idArg =
                ArgumentCaptor.forClass(String.class);
        verify(allergyRepository).deleteAllergy(idArg.capture(), deleteArg.capture());
        var capturedDeleted = deleteArg.getValue();
        assertThat(capturedDeleted).isEqualTo(deleted.getTitle());
    }
}