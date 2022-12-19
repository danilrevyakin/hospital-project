package com.example.hospitalproject.medicalCard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MedicalCardNotFoundException extends RuntimeException{
    public MedicalCardNotFoundException() {
    }
}
