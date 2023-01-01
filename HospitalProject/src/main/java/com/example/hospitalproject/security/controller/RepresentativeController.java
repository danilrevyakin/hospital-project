package com.example.hospitalproject.security.controller;

import com.example.hospitalproject.security.node.Role;
import com.example.hospitalproject.security.service.RepresentativeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/representative")
public class RepresentativeController {
    private final RepresentativeService representativeService;

    public RepresentativeController(RepresentativeService representativeService) {
        this.representativeService = representativeService;
    }

    @GetMapping("/create")
    public String createRepresentative(){
        representativeService.createRepresentative(Role.PATIENT);
        representativeService.createRepresentative(Role.DOCTOR);
        return "nodes created!";
    }
}
