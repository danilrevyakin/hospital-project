package com.example.hospitalproject.medicalCard.integration;

import com.example.hospitalproject.medicalCard.model.Allergy;
import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.repository.AllergyRepository;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import com.example.hospitalproject.medicalCard.service.implementation.AllergyServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class AllergyServiceIntegrationTest {

    @Autowired
    private AllergyRepository allergyRepository;
    @Autowired
    private MedicalCardRepository repository;
    @Autowired
    private AllergyServiceImpl service;
    private static final String id = "testID";

    @AfterEach
    void deleteCard() {
        repository.deleteById(id);
    }

    @Test
    void add() {
        //given
        Allergy allergy = new Allergy(
                "honey",
                "dead"
        );
        Allergy allergyParam = new Allergy(
                "apple           juiCe     ",
                "           dead         1        100"
        );
        Allergy expectedAllergy = new Allergy(
                "apple juice",
                "dead 1 100"
        );
        final MedicalCard card = new MedicalCard(
                id,
                Set.of(allergy),
                null,
                null,
                null
        );
        repository.save(card);
        var expected = Set.of(allergy, expectedAllergy);
        //when
        service.add(id, allergyParam);
        //then
        Set<Allergy> allergies = allergyRepository.getAllAllergiesById(id);
        assertThat(allergies).isEqualTo(expected);
        compareAllergiesByReactions(allergies, expected);
    }

    @Test
    void update() {
        //given
        final var old = new Allergy(
                "honey",
                "dead"
        );
        Allergy allergyParam = new Allergy(
                "apple           juiCe     ",
                "           dead         1        100"
        );
        Allergy expectedAllergy = new Allergy(
                "apple juice",
                "dead 1 100"
        );
        final MedicalCard card = new MedicalCard(
                id,
                Set.of(old),
                null,
                null,
                null
        );
        repository.save(card);
        final var expected = Set.of(expectedAllergy);
        //when
        service.update(id, old.getTitle(), allergyParam);
        //then
        var actual = allergyRepository.getAllAllergiesById(id);
        assertThat(actual).isEqualTo(expected);
        compareAllergiesByReactions(actual, expected);
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
        repository.save(card);
        final var expected = Set.of(old);
        //when
        service.delete(id, deleted.getTitle());
        //then
        final var actual = allergyRepository.getAllAllergiesById(id);
        assertThat(actual).isEqualTo(expected);
        compareAllergiesByReactions(actual, expected);
    }

    void compareAllergiesByReactions(Set<Allergy> allergies,Set<Allergy> expected){
        Map<String, Allergy> mapOfExpected = new HashMap<>();
        expected.forEach(x->mapOfExpected.put(x.getTitle(), x));
        allergies.forEach(x->{
            String reaction = mapOfExpected.get(x.getTitle()).getReaction();
            assertThat(x.getReaction()).isEqualTo(reaction);
        });
    }
}