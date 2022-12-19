package com.example.hospitalproject.medicalCard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IllegalDoctorException extends RuntimeException{
    public IllegalDoctorException(String message) {
        super(message);
    }
}
