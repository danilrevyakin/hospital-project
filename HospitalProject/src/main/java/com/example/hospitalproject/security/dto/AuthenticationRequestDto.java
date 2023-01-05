package com.example.hospitalproject.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationRequestDto {
    private String credentials;
    private String password;
}