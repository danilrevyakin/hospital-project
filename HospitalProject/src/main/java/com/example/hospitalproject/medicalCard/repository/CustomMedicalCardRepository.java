package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.Allergy;
import com.example.hospitalproject.medicalCard.model.MedicalRecord;

import java.util.List;

public interface CustomMedicalCardRepository {
    void addAllergy(String id, Allergy allergy);

    void updateAllergy(String id, String title, Allergy allergy);

    void deleteAllergy(String id, String title, Allergy allergy);

    void addMedicalRecord(String id, MedicalRecord record);

    void addBadHabit(String id, String badHabit);

    void deleteBadHabit(String id, String badHabit);

    void updateBadHabit(String id, String oldBadHabit, String newBadHabit);

    void updateAllBadHabits(String id, List<String> newBadHabits);

}
