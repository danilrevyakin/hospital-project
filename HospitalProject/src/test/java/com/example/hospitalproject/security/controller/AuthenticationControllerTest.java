package com.example.hospitalproject.security.controller;

import com.example.hospitalproject.security.dto.AuthenticationRequestDto;
import com.example.hospitalproject.security.dto.RegistrationDto;
import com.example.hospitalproject.security.exception.InvalidPasswordException;
import com.example.hospitalproject.security.exception.NotFoundException;
import com.example.hospitalproject.security.exception.UserExistsException;
import com.example.hospitalproject.security.node.Role;
import com.example.hospitalproject.security.node.User;
import com.example.hospitalproject.security.repository.RepresentativeRepository;
import com.example.hospitalproject.security.repository.UserRepository;
import com.example.hospitalproject.security.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthenticationControllerTest {
    private static final String DEFAULT_USER_EMAIL = "email@email.em";
    private static final String WRONG_USER_EMAIL = "email1@email.em";
    private static final String DEFAULT_USER_PHONE_NUMBER = "+3800000000";
    private static final String DEFAULT_USER_FIRST_NAME = "Petro";
    private static final String DEFAULT_USER_LAST_NAME = "Petrivchuck";
    private static final boolean DEFAULT_DOCTOR = false;
    private static final String DEFAULT_USER_PASSWORD = "password";
    private static final String WRONG_USER_PASSWORD = "password1";
    private static final Long DEFAULT_ID = 8L;
    private User user;
    private RegistrationDto registrationDto;

    private AuthenticationRequestDto requestDto;

    public BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  RepresentativeRepository representativeRepository;


    @Autowired
    private UserService service;



    @BeforeEach
    void setUp() {
        user = new User();
        registrationDto = new RegistrationDto();
        passwordEncoder = new BCryptPasswordEncoder();
        requestDto = new AuthenticationRequestDto();

        user.setEmail(DEFAULT_USER_EMAIL);
        user.setPhoneNumber(DEFAULT_USER_PHONE_NUMBER);
        user.setPassword(passwordEncoder.encode(DEFAULT_USER_PASSWORD));
        user.setRole(Role.PATIENT);
        user.setId(DEFAULT_ID);
        user.setRepresentative(representativeRepository.getByRole(Role.PATIENT));

        registrationDto.setEmail(DEFAULT_USER_EMAIL);
        registrationDto.setPhoneNumber(DEFAULT_USER_PHONE_NUMBER);
        registrationDto.setPassword(DEFAULT_USER_PASSWORD);
        registrationDto.setDoctor(DEFAULT_DOCTOR);
        registrationDto.setFirstName(DEFAULT_USER_FIRST_NAME);
        registrationDto.setLastName(DEFAULT_USER_LAST_NAME);
        registrationDto.setId(DEFAULT_ID);

        service.registerUser(registrationDto);

        requestDto.setCredentials(DEFAULT_USER_EMAIL);
        requestDto.setPassword(DEFAULT_USER_PASSWORD);
    }

    @AfterEach
    void tearDown(){
        userRepository.deleteUserByEmail(DEFAULT_USER_EMAIL);
    }

    @Test
    void authenticateSuccessTest() {
        User userToCheck = service.getUserByCredentials(requestDto);
        assertThat(userToCheck).isNotNull();
        assertTrue(passwordEncoder.matches(requestDto.getPassword(), userToCheck.getPassword()));
    }

    @Test
    void registrationSuccessTest() {
        userRepository.deleteUserByEmail(DEFAULT_USER_EMAIL);
        service.registerUser(registrationDto);
        Optional<User> userSaved = userRepository.getUserByEmail(DEFAULT_USER_EMAIL);
        assertThat(userSaved).isPresent();
    }

    @Test
    void userNotFoundTest() {
        AuthenticationRequestDto wrongRequest = requestDto;
        wrongRequest.setCredentials(WRONG_USER_EMAIL);
        Throwable exception = assertThrows(NotFoundException.class, () -> service.getUserByCredentials(wrongRequest));
        assertEquals("Invalid credentials. User wasn't found", exception.getMessage());
    }

    @Test
    void invalidPasswordTest() {
        AuthenticationRequestDto wrongRequest = requestDto;
        wrongRequest.setPassword(WRONG_USER_PASSWORD);

        Throwable exception = assertThrows(InvalidPasswordException.class, () -> service.getUserByCredentials(wrongRequest));

        assertEquals("Invalid password", exception.getMessage());
    }

    @Test
    void userAlreadyExistsTest(){
        Throwable exception = assertThrows(UserExistsException.class, () -> service.registerUser(registrationDto));
        assertEquals("Email or phone number already exists", exception.getMessage());
    }
}