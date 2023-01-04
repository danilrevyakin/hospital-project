package com.example.hospitalproject.security.mapper;


import com.example.hospitalproject.security.controller.AuthenticationController;
import com.example.hospitalproject.security.dto.AuthenticationRequestDto;
import com.example.hospitalproject.security.dto.AuthenticationResponseDto;
import com.example.hospitalproject.security.dto.RegistrationDto;
import com.example.hospitalproject.security.node.Role;
import com.example.hospitalproject.security.node.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component("Mapper")
public abstract class UserMapper implements Mapper<User, RegistrationDto, AuthenticationResponseDto>{

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public AuthenticationResponseDto mapEntityToDto(User user) {
        AuthenticationResponseDto dto = new AuthenticationResponseDto();
        dto.setId(user.getId());
        dto.setRole(user.getRole());
        return dto;
    }

    public User mapDtoToEntity(RegistrationDto registrationDto) {
        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setRole(registrationDto.isDoctor() ? Role.DOCTOR : Role.PATIENT);
        user.setPhoneNumber(registrationDto.getPhoneNumber());
        return user;
    }
}
