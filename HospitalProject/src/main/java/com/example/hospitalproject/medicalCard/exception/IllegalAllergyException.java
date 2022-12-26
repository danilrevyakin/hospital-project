package com.example.hospitalproject.medicalCard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IllegalAllergyException extends RuntimeException{
    public IllegalAllergyException(String message) {
        super(message);
    }
}
