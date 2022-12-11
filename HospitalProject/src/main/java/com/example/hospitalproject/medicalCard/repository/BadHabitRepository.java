package com.example.hospitalproject.medicalCard.repository;

public interface BadHabitRepository {
    void addBadHabit(String id, String badHabit);

    void deleteBadHabit(String id, String badHabit);
}
