package com.example.hospitalproject.medicalCard.model;

import java.time.LocalDateTime;
import java.util.Objects;

public record MedicalRecord(String info, String symptoms, String treatment, String doctor, LocalDateTime date, LocalDateTime edited) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalRecord record = (MedicalRecord) o;
        return Objects.equals(doctor, record.doctor) && Objects.equals(date, record.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctor, date);
    }
}
