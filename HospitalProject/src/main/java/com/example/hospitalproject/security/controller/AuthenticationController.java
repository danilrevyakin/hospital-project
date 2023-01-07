package com.example.hospitalproject.security.controller;

import com.example.hospitalproject.security.dto.AuthenticationRequestDto;
import com.example.hospitalproject.security.dto.RegistrationDto;
import com.example.hospitalproject.security.exception.UserExistsException;
import com.example.hospitalproject.security.node.Role;
import com.example.hospitalproject.security.node.User;
import com.example.hospitalproject.security.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/auth")
    public String authenticate(Model model){
        model.addAttribute("authDto", new AuthenticationRequestDto());
        return "auth";
    }

    @PostMapping("/auth")
    public String authenticate(@Valid @ModelAttribute("authDto") AuthenticationRequestDto dto,
                               Model model,
                               HttpSession session)
    {
        User user;
        try{
            user = userService.getUserByCredentials(dto);
            session.setAttribute("id", user.getId());
            return "redirect:/find_doctor";
        } catch (RuntimeException exception){
            model.addAttribute("exception", exception.getMessage());
            return "auth";
        }
    }

    @GetMapping("/register_user")
    public String registerUser(Model model){
        model.addAttribute("dto", new RegistrationDto());
        return "reg";
    }

    @PostMapping("/register_user")
    public String registerUser(@Valid @ModelAttribute("dto") RegistrationDto dto,
                               BindingResult result,
                               Model model,
                               HttpSession session
    ){
        if (result.hasErrors()){
            return "reg";
        }
        try {
            User user = userService.registerUser(dto);
            dto.setId(user.getId());
            session.setAttribute("dto", dto);
            return "redirect:/find_doctor";
        } catch (UserExistsException exception){
            model.addAttribute("credentialsExist", exception.getMessage());
            return "reg";
        }
    }

    @GetMapping("/all_users")
    public String getAllUsers(){
        List<User> list = userService.getAllByRole(Role.PATIENT);
        list.addAll(userService.getAllByRole(Role.DOCTOR));
        return "auth";
    }

    @DeleteMapping("/delete_all")
    public void deleteAll(){
        userService.deleteAllUsers();
    }

    @GetMapping("/get/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
    }
}
