package com.example.hospitalproject.security.controller;

import com.example.hospitalproject.security.dto.AuthenticationRequestDto;
import com.example.hospitalproject.security.dto.RegistrationDto;
import com.example.hospitalproject.security.exception.NotFoundException;
import com.example.hospitalproject.security.exception.UserExistsException;
import com.example.hospitalproject.security.node.Role;
import com.example.hospitalproject.security.node.User;
import com.example.hospitalproject.security.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthenticationController {
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
            //TODO: return "error" html or smth
            return "auth";
        }
    }

    @GetMapping("/auth")
    public String authenticate(){
        return "auth";
    }


    @PostMapping("/register_user")
    public String registerUser(@ModelAttribute("dto") RegistrationDto dto, HttpSession session) {
        try {
            User user = userService.registerUser(dto);
            dto.setId(user.getId());
            session.setAttribute("dto", dto);
            return "redirect:/find_doctor";
        }catch (UserExistsException exception){
            //TODO: do smth
            return "reg";
        }
    }

    @GetMapping("/register_user")
    public String registerUser(Model model){
        model.addAttribute("dto", new RegistrationDto());
        return "reg";
    }

    @GetMapping("/all_users")
    public String getAllUsers(){
        List<User> list = userService.getAllByRole(Role.DOCTOR);
        System.out.println(list);
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
