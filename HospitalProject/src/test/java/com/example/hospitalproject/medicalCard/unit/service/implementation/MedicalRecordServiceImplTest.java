package com.example.hospitalproject.medicalCard.unit.service.implementation;

import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import com.example.hospitalproject.medicalCard.repository.MedicalRecordRepository;
import com.example.hospitalproject.medicalCard.service.implementation.MedicalRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceImplTest {
    @Mock
    private MedicalRecordRepository recordRepository;
    @Mock
    private MedicalCardRepository repository;
    private MedicalRecordServiceImpl service;
    private static final String id = "testID";

    @BeforeEach
    void setUp() {
        service = new MedicalRecordServiceImpl(repository, recordRepository, Clock.systemUTC());
    }

    @Test
    void addMedicalRecord() {
        //given
        MedicalRecord keyRecord = new MedicalRecord(
                "Covid20",
                "headache and heart pain",
                "vitamins",
                "Brad",
                null,
                null
        );
        given(repository.existsById(id))
                .willReturn(true);
        //when
        service.addMedicalRecord(id, keyRecord);
        //then
        var keyArg =
                ArgumentCaptor.forClass(MedicalRecord.class);
        ArgumentCaptor<String> idArg =
                ArgumentCaptor.forClass(String.class);
        verify(recordRepository)
                .addMedicalRecord(idArg.capture(), keyArg.capture());
        var capturedKey = keyArg.getValue();
        assertThat(capturedKey).isEqualTo(keyRecord);
    }

    @Test
    void getMedicalRecord() {
        //given
        LocalDateTime key = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        MedicalRecord keyRecord = new MedicalRecord(
                "Covid20",
                "headache and heart pain",
                "vitamins",
                "Brad",
                key,
                null
        );
        given(repository.existsById(id))
                .willReturn(true);
        given(recordRepository.getMedicalRecordsByIdAndDate(id, key))
                .willReturn(List.of(keyRecord));
        //when
        service.getMedicalRecord(id, key);
        //then
        var keyArg =
                ArgumentCaptor.forClass(LocalDateTime.class);
        ArgumentCaptor<String> idArg =
                ArgumentCaptor.forClass(String.class);
        verify(recordRepository)
                .getMedicalRecordsByIdAndDate(idArg.capture(), keyArg.capture());
        var capturedKey = keyArg.getValue();
        assertThat(capturedKey).isEqualTo(key);
    }

    @Test
    void getAllMedicalRecordsById() {
        //given
        given(repository.existsById(id))
                .willReturn(true);
        //when
        service.getAllMedicalRecordsById(id);
        //then
        verify(recordRepository).getMedicalRecordsById(id);
    }

    @Test
    void updateMedicalRecord() {
        //given
        LocalDateTime key = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        MedicalRecord keyRecord = new MedicalRecord(
                "Covid20",
                "headache and heart pain",
                "vitamins",
                "Brad",
                key,
                null
        );
        MedicalRecord updatedRecord = new MedicalRecord(
                "Covid20000",
                "headache and heart pain",
                "vitamins and water",
                "Brad",
                key,
                null
        );
        given(repository.existsById(id))
                .willReturn(true);
        given(recordRepository.getMedicalRecordsByIdAndDate(id, key))
                .willReturn(List.of(keyRecord));
        //when
        service.updateMedicalRecord(id, updatedRecord.getDoctor(), updatedRecord);
        //then
        var updatedRecordArg =
                ArgumentCaptor.forClass(MedicalRecord.class);
        var keyArg =
                ArgumentCaptor.forClass(LocalDateTime.class);
        ArgumentCaptor<String> idArg =
                ArgumentCaptor.forClass(String.class);
        verify(recordRepository)
                .updateMedicalRecord(idArg.capture(),
                        keyArg.capture(), updatedRecordArg.capture());
        var capturedKey = keyArg.getValue();
        assertThat(capturedKey).isEqualTo(key);
        var capturedKeyRecord = updatedRecordArg.getValue();
        assertThat(capturedKeyRecord).isEqualTo(updatedRecord);
    }

    @Test
    void deleteMedicalRecord() {
        //given
        LocalDateTime key = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        MedicalRecord keyRecord = new MedicalRecord(
                "Covid20",
                "headache and heart pain",
                "vitamins",
                "Brad",
                key,
                null
        );
        given(repository.existsById(id))
                .willReturn(true);
        given(recordRepository.getMedicalRecordsByIdAndDate(id, key))
                .willReturn(List.of(keyRecord));
        //when
        service.deleteMedicalRecord(id, key, keyRecord.getDoctor());
        //then
        var keyArg =
                ArgumentCaptor.forClass(LocalDateTime.class);
        ArgumentCaptor<String> idArg =
                ArgumentCaptor.forClass(String.class);
        verify(recordRepository)
                .deleteMedicalRecord(idArg.capture(),
                        keyArg.capture());
        var capturedKey = keyArg.getValue();
        assertThat(capturedKey).isEqualTo(key);
    }
}