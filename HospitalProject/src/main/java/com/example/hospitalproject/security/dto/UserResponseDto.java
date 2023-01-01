package com.example.hospitalproject.security.dto;

import com.example.hospitalproject.security.node.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String email;
    private String phoneNumber;
    private Role role;
}