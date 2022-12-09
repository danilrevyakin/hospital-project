package com.example.hospitalproject.medicalCard.model;

import java.time.LocalDate;

public record MedicalRecord(String info, String symptoms, String treatment, LocalDate date, LocalDate edited) {
}
