package com.example.hospitalproject.security.controller;

import com.example.hospitalproject.security.dto.AuthenticationRequestDto;
import com.example.hospitalproject.security.dto.RegistrationDto;
import com.example.hospitalproject.security.exception.NotFoundException;
import com.example.hospitalproject.security.exception.UserExistsException;
import com.example.hospitalproject.security.node.Role;
import com.example.hospitalproject.security.node.User;
import com.example.hospitalproject.security.service.UserService;
import com.example.hospitalproject.userInfo.model.Doctor;
import com.example.hospitalproject.userInfo.model.DoctorType;
import com.example.hospitalproject.userInfo.model.Patient;
import com.example.hospitalproject.userInfo.model.UserInfo;
import com.example.hospitalproject.userInfo.repository.DoctorRepository;
import com.example.hospitalproject.userInfo.repository.PatientRepository;
import com.example.hospitalproject.userInfo.repository.UserInfoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Controller
public class AuthenticationController {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;

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

            UserInfo userInfo = createUserInfo(dto);
            userInfoRepository.save(userInfo);
            setRoleForUser(dto, userInfo);

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

    private UserInfo createUserInfo(RegistrationDto registrationDto) {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(registrationDto.getEmail());
        userInfo.setFirstName(registrationDto.getFirstName());
        userInfo.setLastName(registrationDto.getLastName());
        userInfo.setPhone(registrationDto.getPhoneNumber());
        userInfo.setBirthday(Date.valueOf(registrationDto.getBirth()));
        userInfo.setId(registrationDto.getId());

        return userInfo;
    }

    private void setRoleForUser(RegistrationDto registrationDto, UserInfo userInfo){
        if(registrationDto.isDoctor())
            doctorRepository.save(new Doctor(registrationDto.getId(), userInfo, DoctorType.PSYCHIATRIST));
        else
            patientRepository.save(new Patient(registrationDto.getId(), userInfo));
    }
}
