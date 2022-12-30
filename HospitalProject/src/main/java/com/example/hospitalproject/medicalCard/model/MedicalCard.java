package com.example.hospitalproject.medicalCard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class MedicalCard {
    @Id
    private String id;
    private Set<Allergy> allergies;
    private Set<String> badHabits;
    private List<MedicalRecord> records;
    private LocalDate created;

    public enum field{
        id("_"), allergies, badHabits, records, created;

        public final String nameDot$;
        public final String n;

        field(String before) {
            nameDot$ = name() + ".$";
            n = before + name();
        }

        field(){
            nameDot$ = name() + ".$";
            n = name();
        }
    }
}
