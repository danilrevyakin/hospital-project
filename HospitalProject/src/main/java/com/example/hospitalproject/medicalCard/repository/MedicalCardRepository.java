package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.Set;

public interface MedicalCardRepository extends MongoRepository<MedicalCard, String>{

    @Override
    Optional<MedicalCard> findById(String s);

    @Override
    void deleteById(String s);

    @Override
    boolean existsById(String s);

    boolean existsMedicalCardByIdAndBadHabitsContaining(String id, Set<String> habits);
}
