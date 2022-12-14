package com.example.hospitalproject.userInfo.controller;

import com.example.hospitalproject.userInfo.model.UserInfo;
import com.example.hospitalproject.userInfo.repository.DoctorRepository;
import com.example.hospitalproject.userInfo.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/hospital")
    public String showUsers(Model model){
        List<UserInfo> userInfos = userInfoRepository.findAll();
        model.addAttribute("info", userInfos);
        return "hospital";
    }

    @GetMapping("/hospital/{id}")
    public String showUserPage(@PathVariable(value = "id") long id, Model model){
        UserInfo user = null;
        Optional<UserInfo> userInfo = userInfoRepository.findById(id);
        if(userInfo.isPresent())
            user = userInfo.get();

        model.addAttribute("user", user);
        model.addAttribute("role", getRole(user));

        return "userProfile";
    }

    private String getRole(UserInfo id){
        return doctorRepository.findByUserId(id).isEmpty() ? "patient" : "doctor";
    }
}
