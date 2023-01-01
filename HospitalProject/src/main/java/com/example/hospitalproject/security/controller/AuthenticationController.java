package com.example.hospitalproject.security.controller;

import com.example.hospitalproject.security.dto.AuthenticationRequestDto;
import com.example.hospitalproject.security.dto.AuthenticationResponseDto;
import com.example.hospitalproject.security.dto.RegistrationDto;
import com.example.hospitalproject.security.exception.NotFoundException;
import com.example.hospitalproject.security.exception.UserExistsException;
import com.example.hospitalproject.security.node.User;
import com.example.hospitalproject.security.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public AuthenticationResponseDto authenticate(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        User user;
        try{
            user = userService.getUserByCredentials(authenticationRequestDto);
        } catch (NotFoundException exception){
            return null;
        }
        return generateResponse(user);
    }

    private AuthenticationResponseDto generateResponse(User user) {
        AuthenticationResponseDto responseDto = new AuthenticationResponseDto();
        responseDto.setId(user.getId());
        responseDto.setRole(user.getRole());
        return responseDto;
    }

    @PostMapping("/registration")
    public RegistrationDto registerUser(@RequestBody RegistrationDto registrationDto) {
        try {
            User user = userService.registerUser(registrationDto);
            registrationDto.setId(user.getId());
        }catch (UserExistsException exception){
            return null;
        }
        return registrationDto;
    }
}
