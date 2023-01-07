package com.example.hospitalproject.security.dto;

import com.example.hospitalproject.security.validation.constraint.EmailConstraint;
import com.example.hospitalproject.security.validation.constraint.NameConstraint;
import com.example.hospitalproject.security.validation.constraint.PasswordConstraint;
import com.example.hospitalproject.security.validation.constraint.PhoneNumberConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {
    private Long id;

    @NameConstraint
    private String firstName;

    @NameConstraint
    private String lastName;

    @EmailConstraint
    private String email;

    @PhoneNumberConstraint
    private String phoneNumber;

    @NotBlank(message = "Must not be blank")
    private String birth;

    private boolean doctor;

    private String specialization;

    @PasswordConstraint
    private String password;
}
