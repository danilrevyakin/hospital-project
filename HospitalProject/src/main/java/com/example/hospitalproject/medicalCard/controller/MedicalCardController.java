package com.example.hospitalproject.medicalCard.controller;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import com.example.hospitalproject.medicalCard.service.MedicalCardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medical-card")
@AllArgsConstructor
public class MedicalCardController {

    private final MedicalCardService service;

    @GetMapping("/all")
    public ResponseEntity<List<MedicalCard>> fetchAllMedicalCards() {
        List<MedicalCard> allCards = service.getAllCards();
        return new ResponseEntity<>(allCards, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")//id is the same as SQL id of Patient
    public ResponseEntity<MedicalCard> getMedicalCard(@PathVariable("id") String id) {
        MedicalCard card = service.getMedicalCardById(id);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @PostMapping("/create-by-id/{id}")//id is the same as SQL id of Patient
    public ResponseEntity<MedicalCard> createById(@PathVariable("id") String id) {
        MedicalCard card = service.createEmptyMedicalCard(id);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }


}
