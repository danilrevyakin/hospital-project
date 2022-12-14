package com.example.hospitalproject.medicalCard.model;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MedicalRecord {
    private String info;
    private String symptoms;
    private String treatment;
    private String doctor;
    private LocalDateTime date;
    private LocalDateTime edited;

//    public MedicalRecord(String info, String symptoms, String treatment, String doctor, LocalDateTime date, LocalDateTime edited) {
//        this.info = info;
//        this.symptoms = symptoms;
//        this.treatment = treatment;
//        this.doctor = doctor;
//        setDate(date);
//        setEdited(edited);
//    }
//
//    private static LocalDateTime truncateDates(LocalDateTime date){
//        return date.truncatedTo(ChronoUnit.SECONDS);
//    }
//
//    private static LocalDateTime truncateOrNull(LocalDateTime date){
//        return date != null? truncateDates(date) : null;
//    }
//
//    public void setDate(LocalDateTime date) {
//        this.date = truncateOrNull(date);
//    }
//
//    public void setEdited(LocalDateTime edited) {
//        this.edited = truncateOrNull(edited);
//    }

}
