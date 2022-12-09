package com.example.hospitalproject.medicalCard;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MedicalCardRepository extends MongoRepository<MedicalCard, String> {
}
