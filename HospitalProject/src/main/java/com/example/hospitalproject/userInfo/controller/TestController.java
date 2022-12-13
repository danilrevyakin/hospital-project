package com.example.hospitalproject.userInfo.controller;

import com.example.hospitalproject.userInfo.model.UserInfo;
import com.example.hospitalproject.userInfo.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @GetMapping("/test")
    public String testMethod(Model model){
        Iterable<UserInfo> userInfos = userInfoRepository.findAll();
        model.addAttribute("info", userInfos);
        return "test";
    }
}
