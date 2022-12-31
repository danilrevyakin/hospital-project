package com.example.hospitalproject.medicalCard.integration;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import com.example.hospitalproject.medicalCard.repository.MedicalRecordRepository;
import com.example.hospitalproject.medicalCard.service.implementation.MedicalRecordServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class MedicalRecordServiceIntegrationTest {
    @Autowired
    private MedicalRecordRepository recordRepository;
    @Autowired
    private MedicalCardRepository repository;
    private Clock clock;
    private MedicalRecordServiceImpl service;
    private static final String id = "testID";

    @BeforeEach
    void initService(){
        clock = Clock.fixed(Instant.now(), ZoneId.of("GMT"));
        service = new MedicalRecordServiceImpl(repository, recordRepository, clock);
    }

    @AfterEach
    void deleteCard() {
        repository.deleteById(id);
    }

    @Test
    void addMedicalRecord() {
        //given
        LocalDateTime date = LocalDateTime.now().minus(20, ChronoUnit.YEARS)
                .truncatedTo(ChronoUnit.MILLIS);
        MedicalRecord recordParam = new MedicalRecord(
                "       Covid      20         ",
                "headache                and heart                  pain                ",
                "vitamins                   ",
                "                 Brad                     ",
                date,
                date
        );
        MedicalRecord expectedRecord = new MedicalRecord(
                "Covid 20",
                "headache and heart pain",
                "vitamins",
                "Brad",
                LocalDateTime.now(clock).truncatedTo(ChronoUnit.MILLIS),
                null
        );
        final MedicalCard card = new MedicalCard(
                id,
                null,
                null,
                null,
                null
        );
        repository.save(card);
        //when
        service.addMedicalRecord(id, recordParam);
        //then
        List<MedicalRecord> actual = recordRepository.getMedicalRecordsById(id);
        assertThat(actual).isEqualTo(List.of(expectedRecord));
    }

    @Test
    void updateMedicalRecord() {
        //given
        LocalDateTime key = LocalDateTime.now(clock).minusHours(1).truncatedTo(ChronoUnit.MILLIS);
        String doctor = "Brad";
        MedicalRecord keyRecord = new MedicalRecord(
                "before updating",
                "h ea da che and hea rt pa in",
                "v ita mi ns",
                doctor,
                key,
                null
        );
        LocalDateTime date = LocalDateTime.now(clock)
                .truncatedTo(ChronoUnit.MILLIS);
        MedicalRecord recordParam = new MedicalRecord(
                "       Covid      20         ",
                "headache                and heart                  pain                ",
                "vitamins                   ",
                doctor,
                key,
                key
        );
        MedicalRecord expectedRecord = new MedicalRecord(
                "Covid 20",
                "headache and heart pain",
                "vitamins",
                doctor,
                key,
                date
        );
        final MedicalCard card = new MedicalCard(
                id,
                null,
                null,
                List.of(keyRecord),
                null
        );
        repository.save(card);
        //when
        service.updateMedicalRecord(id, doctor, recordParam);
        //then
        List<MedicalRecord> actual = recordRepository.getMedicalRecordsById(id);
        assertThat(actual).isEqualTo(List.of(expectedRecord));
    }

    @Test
    void deleteMedicalRecord() {
        //given
        LocalDateTime key = LocalDateTime.now(clock).minusHours(1).truncatedTo(ChronoUnit.MILLIS);
        String doctor = "Brad";
        MedicalRecord keyRecord = new MedicalRecord(
                "before updating",
                "h ea da che and hea rt pa in",
                "v ita mi ns",
                doctor,
                key,
                null
        );
        final MedicalCard card = new MedicalCard(
                id,
                null,
                null,
                List.of(keyRecord),
                null
        );
        repository.save(card);
        //when
        service.deleteMedicalRecord(id, key, doctor);
        //then
        List<MedicalRecord> actual = recordRepository.getMedicalRecordsById(id);
        assertThat(actual).isEqualTo(List.of());
    }
}