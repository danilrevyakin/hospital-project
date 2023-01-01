package com.example.hospitalproject.security.controller;

import com.example.hospitalproject.security.dto.AuthenticationRequestDto;
import com.example.hospitalproject.security.dto.AuthenticationResponseDto;
import com.example.hospitalproject.security.dto.RegistrationDto;
import com.example.hospitalproject.security.exception.InvalidPasswordException;
import com.example.hospitalproject.security.exception.NotFoundException;
import com.example.hospitalproject.security.exception.UserExistsException;
import com.example.hospitalproject.security.mapper.Mapper;
import com.example.hospitalproject.security.node.Role;
import com.example.hospitalproject.security.node.User;
import com.example.hospitalproject.security.repository.RepresentativeRepository;
import com.example.hospitalproject.security.repository.UserRepository;
import com.example.hospitalproject.security.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {
    private static final String DEFAULT_USER_EMAIL = "email@email.em";
    private static final String WRONG_USER_EMAIL = "email1@email.em";
    private static final String DEFAULT_USER_PHONE_NUMBER = "+380999999999";
    private static final String DEFAULT_USER_FIRST_NAME = "Petro";
    private static final String DEFAULT_USER_LAST_NAME = "Petrivchuck";
    private static final boolean DEFAULT_DOCTOR = false;
    private static final String DEFAULT_USER_PASSWORD = "password";
    private static final String WRONG_USER_PASSWORD = "password1";
    private static final Long DEFAULT_ID = 1L;

    private User user;
    private User fakeUser;
    private RegistrationDto registrationDto;

    private AuthenticationRequestDto requestDto;
    private AuthenticationResponseDto userResponseDto;

    @Mock
    private UserRepository userRepository;

    @Mock
    private  RepresentativeRepository representativeRepository;

    @Mock
    private Mapper<User, RegistrationDto, AuthenticationResponseDto> userMapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService testInstance;



    @BeforeEach
    void setUp() {
        user = new User();
        registrationDto = new RegistrationDto();
        passwordEncoder = new BCryptPasswordEncoder();
        userResponseDto = new AuthenticationResponseDto();
        requestDto = new AuthenticationRequestDto();

        user.setEmail(DEFAULT_USER_EMAIL);
        user.setPhoneNumber(DEFAULT_USER_PHONE_NUMBER);
        user.setPassword(passwordEncoder.encode(DEFAULT_USER_PASSWORD));
        user.setId(DEFAULT_ID);

        fakeUser = user;

        registrationDto.setEmail(DEFAULT_USER_EMAIL);
        registrationDto.setPhoneNumber(DEFAULT_USER_PHONE_NUMBER);
        registrationDto.setPassword(DEFAULT_USER_PASSWORD);
        registrationDto.setDoctor(DEFAULT_DOCTOR);
        registrationDto.setFirstName(DEFAULT_USER_FIRST_NAME);
        registrationDto.setLastName(DEFAULT_USER_LAST_NAME);
        registrationDto.setId(DEFAULT_ID);

        userResponseDto.setId(DEFAULT_ID);
        userResponseDto.setRole(Role.PATIENT);

        requestDto.setCredentials(DEFAULT_USER_EMAIL);
        requestDto.setPassword(DEFAULT_USER_PASSWORD);

    }

    @Test
    void authenticateSuccess() {
        when(userRepository.getUserByEmail(DEFAULT_USER_EMAIL)).thenReturn(Optional.of(user));
        when(userMapper.mapEntityToDto(user)).thenReturn(userResponseDto);

        User actualUser = testInstance.getUserByCredentials(requestDto);
        AuthenticationResponseDto actualDto = userMapper.mapEntityToDto(actualUser);

        assertThat(actualDto).isNotNull()
                .isEqualTo(userResponseDto);
    }

    @Test
    void authenticateNotFound() {
        lenient().when(userRepository.getUserByEmail(DEFAULT_USER_EMAIL)).thenReturn(Optional.of(user));
        lenient().when(userMapper.mapEntityToDto(user)).thenReturn(userResponseDto);

        AuthenticationRequestDto wrongRequest = requestDto;
        wrongRequest.setCredentials(WRONG_USER_EMAIL);

        Throwable exception = assertThrows(NotFoundException.class, () -> testInstance.getUserByCredentials(wrongRequest));

        assertEquals("Invalid credentials. User wasn't found", exception.getMessage());
    }

    @Test
    void authenticateInvalidPassword() {
        lenient().when(userRepository.getUserByEmail(DEFAULT_USER_EMAIL)).thenReturn(Optional.of(user));
        lenient().when(userMapper.mapEntityToDto(user)).thenReturn(userResponseDto);

        AuthenticationRequestDto wrongRequest = requestDto;
        wrongRequest.setPassword(WRONG_USER_PASSWORD);

        Throwable exception = assertThrows(InvalidPasswordException.class, () -> testInstance.getUserByCredentials(wrongRequest));

        assertEquals("Invalid password", exception.getMessage());
    }

    @Test
    void registerUserSuccess() {
        when(userMapper.mapDtoToEntity(registrationDto)).thenReturn(user);

        testInstance.registerUser(registrationDto);

        verify(userRepository).save(user);
    }

    @Test
    void registerUserExists() {
        //TODO
    }
}