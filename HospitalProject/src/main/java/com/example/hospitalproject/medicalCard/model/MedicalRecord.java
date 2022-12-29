package com.example.hospitalproject.medicalCard.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecord {
    private String info;
    private String symptoms;
    private String treatment;
    private String doctor;
    private LocalDateTime date;
    private LocalDateTime edited;
}
