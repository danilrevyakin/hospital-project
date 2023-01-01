package com.example.hospitalproject.security.dto;

import com.example.hospitalproject.security.node.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponseDto {
    private Long id;
    private Role role;
}
