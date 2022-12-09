package com.example.hospitalproject.medicalCard;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medical-card")
@AllArgsConstructor
public class MedicalCardController {

    private final MedicalCardService service;

    @GetMapping("/all")
    public List<MedicalCard> fetchAllMedicalCards(){
        return service.getAllCards();
    }

    @GetMapping("/get/{medicalCardKey}")//medicalCardKey is the same as SQL id of Patient
    public MedicalCard getMedicalCard(@PathVariable("medicalCardKey") Long key){
        return service.getMedicalCardByKey(key);
    }

//    @PutMapping("/add-allergy/{medicalCardKey}/{title}/{reaction}")
//    public String addAllergy(@PathVariable("title") String title, @PathVariable("reaction") String reaction){
//        return "success";
//    }
}
