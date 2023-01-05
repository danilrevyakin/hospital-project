package com.example.hospitalproject.security.controller;

import com.example.hospitalproject.security.dto.AuthenticationRequestDto;
import com.example.hospitalproject.security.dto.AuthenticationResponseDto;
import com.example.hospitalproject.security.dto.RegistrationDto;
import com.example.hospitalproject.security.exception.NotFoundException;
import com.example.hospitalproject.security.exception.UserExistsException;
import com.example.hospitalproject.security.node.User;
import com.example.hospitalproject.security.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@Controller
public class AuthenticationController {
    private static final String $ = File.separator;
    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth")
    public String authenticate(@RequestParam String credentials, @RequestParam String password, Model model) {
        User user;
        try{
            user = userService.getUserByCredentials(new AuthenticationRequestDto(credentials, password));
            model.addAttribute("id", user.getId());
            return "redirect:/doctors";
        } catch (NotFoundException exception){
            //TODO return "error" html or smth
            return "auth";
        }
    }

    @GetMapping("/auth")
    public String authenticate(Model model){
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

    @GetMapping("/this_is_for_test")
    public String redirectToDoctors(Model model){
        RegistrationDto registrationDto = new RegistrationDto(1L, "Pavlo", "Pavlov", "pavlo@gmail.com", "+380999999999", false, "password");
        userService.registerUser(registrationDto);
        model.addAttribute("registrationDto", registrationDto);
        return "redirect:/doctors";
    }
}
