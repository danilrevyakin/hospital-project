package com.example.hospitalproject.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {
    @NotNull
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String phoneNumber;

    @NotNull
    private Date birth;

    private boolean doctor;

    @NotNull(message = "Password can not be null")
    @Size(min = 8, message = "Password length should consist of at least of 8 characters")
    private String password;

    public RegistrationDto(String firstName, String lastName, String email, String phoneNumber, Date birth, boolean doctor, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.doctor = doctor;
        this.password = password;
    }
}
