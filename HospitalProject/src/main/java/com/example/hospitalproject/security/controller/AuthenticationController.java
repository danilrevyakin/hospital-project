package com.example.hospitalproject.security.controller;

import com.example.hospitalproject.security.dto.AuthenticationRequestDto;
import com.example.hospitalproject.security.dto.AuthenticationResponseDto;
import com.example.hospitalproject.security.dto.RegistrationDto;
import com.example.hospitalproject.security.exception.NotFoundException;
import com.example.hospitalproject.security.exception.UserExistsException;
import com.example.hospitalproject.security.node.User;
import com.example.hospitalproject.security.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.sql.Date;

@Controller
public class AuthenticationController {
    private static final String $ = File.separator;
    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth")
    public String authenticate(@RequestParam String credentials, @RequestParam String password, HttpSession session) {
        User user;
        try{
            user = userService.getUserByCredentials(new AuthenticationRequestDto(credentials, password));
            session.setAttribute("id", user.getId());
            return "redirect:/find_doctor";
        } catch (NotFoundException exception){
            //TODO return "error" html or smth
            return "auth";
        }
    }

    @GetMapping("/auth")
    public String authenticate(){
        return "auth";
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

    @GetMapping("/registration")
    public String registerUser(Model model){
        return "reg";
    }

    @GetMapping("/redirect")
    public String redirect(Model model){
        return "redirect:/registration";
    }

    @PostMapping("/test")
    public String redirectToDoctors(HttpSession session){
        RegistrationDto registrationDto = new RegistrationDto("Pavlo", "Pavlov", "email@gmail.com", "+380999999999", Date.valueOf("2002-11-11"),false, "password");
        User user = userService.registerUser(registrationDto);
        registrationDto.setId(user.getId());
        session.setAttribute("registrationDto", registrationDto);
        return "redirect:/doctors";
    }
}
