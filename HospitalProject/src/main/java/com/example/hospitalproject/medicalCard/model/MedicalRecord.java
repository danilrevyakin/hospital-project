package com.example.hospitalproject.medicalCard.model;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class MedicalRecord {
    private String info;
    private String symptoms;
    private String treatment;
    private String doctor;
    private LocalDateTime date;
    private LocalDateTime edited;
}
