package com.example.hospitalproject.medicalCard.unit.repository;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import com.example.hospitalproject.medicalCard.repository.MedicalRecordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static com.example.hospitalproject.medicalCard.unit.repository.AllergyRepositoryTest.getField;

@SpringBootTest
class MedicalRecordRepositoryTest {

    @Autowired
    private MedicalRecordRepository recordRepository;
    @Autowired
    private MedicalCardRepository repository;
    private final String id = "testID";

    @Test
    void getMedicalRecordsByIdAndDate() {
    }

    @Test
    void getMedicalRecordsById() {
        //given
        LocalDateTime first = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        LocalDateTime second = first.plusSeconds(5);
        MedicalRecord record = new MedicalRecord(
                "Covid19",
                "headache",
                "vitamins",
                "Richard",
                first,
                null
        );

        MedicalRecord record2 = new MedicalRecord(
                "Covid20",
                "headache and heart pain",
                "vitamins",
                "Brad",
                second,
                null
        );

        final MedicalCard card = new MedicalCard(
                id,
                null,
                null,
                List.of(record, record2),
                null
        );
        repository.save(card);
        //when
        var actual = recordRepository.getMedicalRecordsById(id);
        //then
        final var expected = getRecords();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void addMedicalRecord() {
        //given
        LocalDateTime first = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        LocalDateTime second = first.plusSeconds(5);
        LocalDateTime third = second.plusSeconds(5);
        MedicalRecord record = new MedicalRecord(
                "Covid19",
                "headache",
                "vitamins",
                "Richard",
                first,
                null
        );

        MedicalRecord record2 = new MedicalRecord(
                "Covid20",
                "headache and heart pain",
                "vitamins",
                "Brad",
                second,
                null
        );
        MedicalRecord last = new MedicalRecord(
                "Covid300",
                "heart pain and mind issues",
                "vitamins and good sleep",
                "House",
                third,
                null
        );

        final MedicalCard card = new MedicalCard(
                id,
                null,
                null,
                List.of(record, record2),
                null
        );
        repository.save(card);
        //when
        recordRepository.addMedicalRecord(id, last);
        var actual = getRecords();
        //then
        final var expected = List.of(record, record2, last);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void updateMedicalRecord() {
        //given
        LocalDateTime first = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        LocalDateTime second = first.plusSeconds(5);
        MedicalRecord record = new MedicalRecord(
                "Covid19",
                "headache",
                "vitamins",
                "Richard",
                first,
                null
        );

        MedicalRecord record2 = new MedicalRecord(
                "Covid20",
                "headache and heart pain",
                "vitamins",
                "Brad",
                second,
                null
        );
        MedicalRecord updated = new MedicalRecord(
                "Covid300",
                "heart pain and mind issues",
                "vitamins and good sleep",
                "House",
                second,
                null
        );

        final MedicalCard card = new MedicalCard(
                id,
                null,
                null,
                List.of(record, record2),
                null
        );
        repository.save(card);
        //when
        recordRepository.updateMedicalRecord(id, second, updated);
        var actual = getRecords();
        //then
        final var expected = List.of(record, updated);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void deleteMedicalRecord() {
        //given
        LocalDateTime first = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        LocalDateTime second = first.plusSeconds(5);
        LocalDateTime third = second.plusSeconds(5);
        MedicalRecord record = new MedicalRecord(
                "Covid19",
                "headache",
                "vitamins",
                "Richard",
                first,
                null
        );

        MedicalRecord record2 = new MedicalRecord(
                "Covid20",
                "headache and heart pain",
                "vitamins",
                "Brad",
                second,
                null
        );
        MedicalRecord last = new MedicalRecord(
                "Covid300",
                "heart pain and mind issues",
                "vitamins and good sleep",
                "House",
                third,
                null
        );

        final MedicalCard card = new MedicalCard(
                id,
                null,
                null,
                List.of(record, record2, last),
                null
        );
        repository.save(card);
        //when
        recordRepository.deleteMedicalRecord(id, second);
        final var actual = getRecords();
        //then
        final var expected = List.of(record, last);
        assertThat(actual).isEqualTo(expected);
    }

    private List<MedicalRecord> getRecords(){
        return getField(repository.findById(id), MedicalCard::getRecords);
    }
}