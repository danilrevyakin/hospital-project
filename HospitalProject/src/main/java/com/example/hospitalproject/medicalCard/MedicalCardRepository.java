package com.example.hospitalproject.medicalCard;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MedicalCardRepository extends MongoRepository<MedicalCard, String> {
    Optional<MedicalCard> findMedicalCardBySqlKey(Long sqlKey);
}
