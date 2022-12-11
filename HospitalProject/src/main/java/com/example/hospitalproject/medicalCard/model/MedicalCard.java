package com.example.hospitalproject.medicalCard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Document
@AllArgsConstructor
public class MedicalCard {
    @Id
    private String id;
    private Set<Allergy> allergies;
    private Set<String> badHabits;
    private List<MedicalRecord> records;
    private LocalDate created;

    public enum field {
        id, allergies, badHabits, records, created
    }
}
