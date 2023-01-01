package com.example.hospitalproject.security.exception;

public class UsersByRoleNotFoundException extends RuntimeException{
    public UsersByRoleNotFoundException() {
        super("Users not found");
    }
}
