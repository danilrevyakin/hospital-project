package com.example.hospitalproject.security.exception;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException() {
        super("Invalid password");
    }
}
