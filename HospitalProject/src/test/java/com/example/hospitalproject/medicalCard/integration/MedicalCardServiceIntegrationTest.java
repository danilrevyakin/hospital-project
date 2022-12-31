package com.example.hospitalproject.medicalCard.integration;

import com.example.hospitalproject.medicalCard.exception.MedicalCardNotFoundException;
import com.example.hospitalproject.medicalCard.model.Allergy;
import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import com.example.hospitalproject.medicalCard.service.implementation.MedicalCardServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class MedicalCardServiceIntegrationTest {
    @Autowired
    private MedicalCardRepository repository;
    @Autowired
    private MedicalCardServiceImpl service;
    private static final String id = "testID";
    private Clock clock;

    @BeforeEach
    void initService(){
        clock = Clock.fixed(Instant.now(), ZoneId.of("GMT"));
        service = new MedicalCardServiceImpl(repository, clock);
    }

    @AfterEach()
    void deleteCard(TestInfo testInfo) {
        if(testInfo.getTags().contains("SkipCleanup")) {
            return;
        }
        repository.deleteById(id);
    }

    @Test
    void getAllCards() {
        //given
        LocalDateTime date = LocalDateTime.now(clock).truncatedTo(ChronoUnit.MILLIS);
        final var record = new MedicalRecord(
                "Covid 20",
                "headache and heart pain",
                "vitamins",
                "Brad",
                date,
                null
        );
        final var allergy = new Allergy(
                "honey",
                "dead"
        );
        final var badHabits = Set.of("smoking");
        final MedicalCard card = new MedicalCard(
                id,
                Set.of(allergy),
                badHabits,
                List.of(record),
                date.toLocalDate()
        );
        repository.save(card);
//        when
        List<MedicalCard> actual = service.getAllCards();
        List<MedicalCard> expected = repository.findAll();
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getMedicalCardById() {
        //given
        LocalDateTime date = LocalDateTime.now(clock).truncatedTo(ChronoUnit.MILLIS);
        final var record = new MedicalRecord(
                "Covid 20",
                "headache and heart pain",
                "vitamins",
                "Brad",
                date,
                null
        );
        final var allergy = new Allergy(
                "honey",
                "dead"
        );
        final var badHabits = Set.of("smoking");
        final MedicalCard expected = new MedicalCard(
                id,
                Set.of(allergy),
                badHabits,
                List.of(record),
                date.toLocalDate()
        );
        repository.save(expected);
//        when
        var actual = service.getMedicalCardById(id);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void createEmptyMedicalCard() {
        //given
        LocalDateTime dateTime = LocalDateTime.now(clock).truncatedTo(ChronoUnit.MILLIS);
        final MedicalCard expected = new MedicalCard(
                id,
                Set.of(),
                Set.of(),
                List.of(),
                dateTime.toLocalDate()
        );
//        when
        service.createEmptyMedicalCard(id);
        //then
        Optional<MedicalCard> byId = repository.findById(id);
        var actual = byId.orElseThrow(MedicalCardNotFoundException::new);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @Tag("SkipCleanup")
    void deleteCardTest() {
        //given
        LocalDateTime date = LocalDateTime.now(clock).truncatedTo(ChronoUnit.MILLIS);
        final var record = new MedicalRecord(
                "Covid 20",
                "headache and heart pain",
                "vitamins",
                "Brad",
                date,
                null
        );
        final var allergy = new Allergy(
                "honey",
                "dead"
        );
        final var badHabits = Set.of("smoking");
        final MedicalCard expected = new MedicalCard(
                id,
                Set.of(allergy),
                badHabits,
                List.of(record),
                date.toLocalDate()
        );
        repository.save(expected);
//        when
        service.deleteCard(id);
        //then
        boolean isPresent = repository.existsById(id);
        assertThat(isPresent).isFalse();
    }
}