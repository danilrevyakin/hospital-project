package com.example.hospitalproject.medicalCard.unit.repository;

import com.example.hospitalproject.medicalCard.exception.MedicalCardNotFoundException;
import com.example.hospitalproject.medicalCard.model.Allergy;
import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.repository.AllergyRepository;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import com.mongodb.Function;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class AllergyRepositoryTest {
    @Autowired
    private AllergyRepository allergyRepository;
    @Autowired
    private MedicalCardRepository repository;
    private final String id = "testID";

    @AfterEach
    void deleteCard() {
        repository.deleteById(id);
    }

    @Test
    void getAllAllergiesById() {
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
                Set.of(allergy2, allergy),
                null,
                null,
                null
        );
        repository.save(card);
        //when
        var actual = allergyRepository.getAllAllergiesById(id);
        //then
        final Set<Allergy> expected = getField(repository.findById(id), MedicalCard::getAllergies);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void addAllergy() {
        //given
        Allergy allergy = new Allergy(
                "honey",
                "dead"
        );
        Allergy allergy2 = new Allergy(
                "apple",
                "dead"
        );

        final Set<Allergy> expected = Set.of(allergy2, allergy);
        final MedicalCard card = new MedicalCard(
                id,
                Set.of(allergy),
                null,
                null,
                null
        );
        repository.save(card);
        //when
        allergyRepository.addAllergy(id, allergy2);
        var actual = getAllergies();
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @SuppressWarnings("all")
    static protected <T extends Collection<?>> T getField(Optional<MedicalCard> optional, Function<MedicalCard, T> getter) {
        if (optional.isEmpty()) {
            throw new MedicalCardNotFoundException();
        }
        var card = optional.get();
        return getter.apply(card);
    }

    private Set<Allergy> getAllergies() {
        return getField(repository.findById(id), MedicalCard::getAllergies);
    }

    @Test
    void updateAllergy() {
        //given
        final String title = "honey";
        final Allergy allergy = new Allergy(
                title,
                "dead"
        );
        final Allergy allergy2 = new Allergy(
                "honey2",
                "dead"
        );
        final Allergy updated = new Allergy(
                "apple",
                "overheating"
        );

        final Set<Allergy> expected = Set.of(updated, allergy2);
        final MedicalCard card = new MedicalCard(
                id,
                Set.of(allergy, allergy2),
                null,
                null,
                null
        );
        repository.save(card);
        //when
        allergyRepository.updateAllergy(id, title, updated);
        var actual = getAllergies();
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void deleteAllergy() {
        //given
        final String title = "honey";
        final Allergy allergy = new Allergy(
                title,
                "dead"
        );
        final Allergy allergy2 = new Allergy(
                "honey2",
                "dead"
        );
        final Allergy allergy3 = new Allergy(
                "apple",
                "overheating"
        );

        final Set<Allergy> expected = Set.of(allergy3, allergy2);
        final MedicalCard card = new MedicalCard(
                id,
                Set.of(allergy, allergy2, allergy3),
                null,
                null,
                null
        );
        repository.save(card);
        //when
        allergyRepository.deleteAllergy(id, title);
        var actual = getAllergies();
        //then
        assertThat(actual).isEqualTo(expected);
    }
}