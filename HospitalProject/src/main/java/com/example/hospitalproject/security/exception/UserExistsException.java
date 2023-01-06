package com.example.hospitalproject.security.exception;

public class UserExistsException extends RuntimeException{
    public UserExistsException() {
        super("Email or phone number already exists");
    }
}
