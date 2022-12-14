package com.example.hospitalproject.medicalCard.repository;

import java.util.Set;

public interface BadHabitRepository {

    Set<String> getBadHabitsById(String id);

    void addBadHabit(String id, String badHabit);

    void deleteBadHabit(String id, String badHabit);
}
