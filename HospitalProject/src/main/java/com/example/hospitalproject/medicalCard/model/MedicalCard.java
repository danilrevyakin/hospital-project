package com.example.hospitalproject.medicalCard.model;

import com.example.hospitalproject.medicalCard.repository.MongoDBField;
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

    public enum field implements MongoDBField {
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

        @Override
        public String n() {
            return n;
        }
    }
}
