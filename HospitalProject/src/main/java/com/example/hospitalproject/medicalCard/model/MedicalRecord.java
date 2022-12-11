package com.example.hospitalproject.medicalCard.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MedicalRecord {
    private String info;
    private String symptoms;
    private String treatment;
    private String doctor;
    private LocalDateTime date;
    private LocalDateTime edited;
}
