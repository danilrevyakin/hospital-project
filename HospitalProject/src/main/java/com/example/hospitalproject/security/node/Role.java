package com.example.hospitalproject.security.node;

public enum Role {
    PATIENT("Patient"),

    DOCTOR("Doctor");

    private final String name;

    Role(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

}
