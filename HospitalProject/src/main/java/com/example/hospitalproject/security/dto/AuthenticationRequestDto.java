package com.example.hospitalproject.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequestDto {
    private String credentials;
    private String password;
}