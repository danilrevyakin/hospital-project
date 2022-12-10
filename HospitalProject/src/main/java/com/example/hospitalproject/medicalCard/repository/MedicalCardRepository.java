package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.MedicalCard;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface MedicalCardRepository extends MongoRepository<MedicalCard, String>, CustomMedicalCardRepository {
    Optional<MedicalCard> findMedicalCardBySqlKey(Long sqlKey);
}
