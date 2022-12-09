package com.example.hospitalproject.medicalCard;

import com.example.hospitalproject.medicalCard.model.Allergy;
import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document
public class MedicalCard {
    @Id
    private String id;
    @Indexed(unique = true)
    private Long sqlKey;
    private List<Allergy> allergies;
    private List<String> badHabits;
    private List<MedicalRecord> records;
    private LocalDate created;

    public MedicalCard(Long sqlKey,
                       List<Allergy> allergies,
                       List<String> badHabits,
                       List<MedicalRecord> records,
                       LocalDate created) {
        this.sqlKey = sqlKey;
        this.allergies = allergies;
        this.badHabits = badHabits;
        this.records = records;
        this.created = created;
    }
}
