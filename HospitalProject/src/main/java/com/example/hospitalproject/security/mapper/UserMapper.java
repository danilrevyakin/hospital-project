package com.example.hospitalproject.security.mapper;


import com.example.hospitalproject.security.dto.RegistrationDto;
import com.example.hospitalproject.security.dto.UserResponseDto;
import com.example.hospitalproject.security.node.Role;
import com.example.hospitalproject.security.node.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component("Mapper")
public class UserMapper implements Mapper<User, RegistrationDto, UserResponseDto>{


    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserResponseDto mapEntityToDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setRole(user.getRole());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        return dto;
    }

    @Override
    public User mapDtoToEntity(RegistrationDto registrationDto) {
        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setRole(registrationDto.isDoctor() ? Role.DOCTOR : Role.PATIENT);
        user.setPhoneNumber(registrationDto.getPhoneNumber());
        return user;
    }
}
