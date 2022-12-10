package com.example.hospitalproject.medicalCard.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document
public class MedicalCard {
    @Id
    private String id;
    private List<Allergy> allergies;
    private List<String> badHabits;
    private List<MedicalRecord> records;
    private LocalDate created;

    public enum field {
        id, sqlKey, allergies, badHabits, records, created
    }

    public MedicalCard(String sqlKey,
                       List<Allergy> allergies,
                       List<String> badHabits,
                       List<MedicalRecord> records,
                       LocalDate created) {
        this.id = sqlKey;
        this.allergies = allergies;
        this.badHabits = badHabits;
        this.records = records;
        this.created = created;
    }
}
