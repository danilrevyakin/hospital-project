package com.example.hospitalproject.security.service;


import com.example.hospitalproject.security.dto.RegistrationDto;
import com.example.hospitalproject.security.dto.UserResponseDto;
import com.example.hospitalproject.security.exception.NotFoundException;
import com.example.hospitalproject.security.exception.UserExistsException;
import com.example.hospitalproject.security.mapper.Mapper;
import com.example.hospitalproject.security.node.Representative;
import com.example.hospitalproject.security.node.Role;
import com.example.hospitalproject.security.node.User;
import com.example.hospitalproject.security.repository.RepresentativeRepository;
import com.example.hospitalproject.security.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final Mapper<User, RegistrationDto, UserResponseDto> userMapper;
    private final UserRepository userRepository;
    private final RepresentativeRepository representativeRepository;

    public UserService(Mapper<User, RegistrationDto, UserResponseDto> userMapper, UserRepository userRepository, RepresentativeRepository representativeRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.representativeRepository = representativeRepository;
    }

    public User getUserByEmail(String credentials){
        return userRepository.getUserByEmail(credentials)
                .orElseGet(() -> getUserByPhoneNumber(credentials));
    }

    public User getUserByPhoneNumber(String credentials){
        Optional<User> user = userRepository.getUserByPhoneNumber(credentials);
        if(user.isPresent()){
            return user.get();
        }else{
            throw new NotFoundException("Invalid credentials. User wasn't found");
        }
    }

    public List<User> getUsersByRole(Role role){
        Optional<List<User>> users = userRepository.getUsersByRole(role);
        if(users.isPresent()){
            return users.get();
        }else {
            throw new NotFoundException("Users with role " + role + " weren't found");
        }
    }

    public void registerUser(RegistrationDto dto){
        Optional<User> userToCheckEmail = userRepository.getUserByEmail(dto.getEmail());
        Optional<User> userToCheckPhoneNumber = userRepository.getUserByPhoneNumber(dto.getPhoneNumber());

        if(userToCheckEmail.isEmpty() && userToCheckPhoneNumber.isEmpty()){
            User user = userMapper.mapDtoToEntity(dto);
            Role role = dto.isDoctor() ? Role.DOCTOR : Role.PATIENT;
            Representative representative = representativeRepository.getByRole(role);
            user.setRepresentative(representative);
            userRepository.save(user);
        }else{
            throw new UserExistsException("Email or phone number already exists");
        }
    }
}
