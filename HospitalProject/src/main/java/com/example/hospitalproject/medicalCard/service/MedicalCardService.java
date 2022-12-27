package com.example.hospitalproject.medicalCard.service;

import com.example.hospitalproject.medicalCard.model.MedicalCard;

import java.util.List;

public interface MedicalCardService {
    List<MedicalCard> getAllCards();
    MedicalCard getMedicalCardById(String id);
    MedicalCard createEmptyMedicalCard(String id);
    List<MedicalCard> deleteCard(String id);
}
