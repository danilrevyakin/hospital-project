package com.example.hospitalproject.userInfo.controller;

import com.example.hospitalproject.userInfo.model.Appointment;
import com.example.hospitalproject.userInfo.model.Doctor;
import com.example.hospitalproject.userInfo.model.UserInfo;
import com.example.hospitalproject.userInfo.repository.AppointmentRepository;
import com.example.hospitalproject.userInfo.repository.DoctorRepository;
import com.example.hospitalproject.userInfo.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UserProfileController {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

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

    @GetMapping("/hospital/{id}/edit")
    public String editUserProfile(@PathVariable(value = "id") long id, Model model){
        UserInfo user = null;
        Optional<UserInfo> userInfo = userInfoRepository.findById(id);
        if(userInfo.isPresent())
            user = userInfo.get();

        model.addAttribute("user", user);
        model.addAttribute("role", getRole(user));

        return "editProfile";
    }

    @PostMapping("/hospital/{id}/edit")
    public String updateUserProfile(@PathVariable(value = "id") long id, @RequestParam String firstName,
                                    @RequestParam String lastName,
                                    @RequestParam String mobilePhone, Model model){
        UserInfo userInfo = userInfoRepository.findById(id).orElseThrow();
        userInfo.setFirstName(firstName);
        userInfo.setLastName(lastName);
        userInfo.setPhone(mobilePhone);

        userInfoRepository.save(userInfo);
        return "redirect:/hospital/" + id;
    }

    @PostMapping("/hospital/{id}/remove")
    public String deleteUserProfile(@PathVariable(value = "id") long id, Model model){
        UserInfo userInfo = userInfoRepository.findById(id).orElseThrow();
        userInfoRepository.delete(userInfo);
        return "redirect:/hospital";
    }

    @GetMapping("/hospital/{id}/appointments")
    public String showDoctorAppointments(@PathVariable(value = "id") long id, Model model){
        UserInfo user = userInfoRepository.findById(id).orElseThrow();
        List<Doctor> doctor = doctorRepository.findByUserId(user);

        List<Appointment> appointmentList = new ArrayList<>();
        if(!doctor.isEmpty())
            appointmentList = appointmentRepository.findByDoctor(doctor.get(0));

        model.addAttribute("appointmentList", appointmentList);
        return "appointments";
    }

    private String getRole(UserInfo id){
        return doctorRepository.findByUserId(id).isEmpty() ? "patient" : "doctor";
    }
}
