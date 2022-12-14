package com.example.hospitalproject.userInfo.controller;

import com.example.hospitalproject.userInfo.model.Doctor;
import com.example.hospitalproject.userInfo.model.DoctorType;
import com.example.hospitalproject.userInfo.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/find_doctor")
    public String findDoctor(Model model){
        return "findDoctor";
    }

    @PostMapping("/find_doctor")
    public String findDoctorBySpeciality(@RequestParam String types,  Model model){
        List<Doctor> doctorList;
        if(types.equals("All"))
            doctorList = doctorRepository.findAll();
       else
            doctorList= doctorRepository.findByType(DoctorType.valueOf(types));

        model.addAttribute("doctors", doctorList);
        return "/doctors";
    }
}
