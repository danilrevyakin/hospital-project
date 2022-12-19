package com.example.hospitalproject.medicalCard.controller;

import com.example.hospitalproject.medicalCard.model.Allergy;
import com.example.hospitalproject.medicalCard.service.AllergyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("medical-card/allergy")
@AllArgsConstructor
public class AllergyController {

    private final AllergyService service;

    @PutMapping("/add/{id}")
    public ResponseEntity<Set<Allergy>> addAllergy(@PathVariable("id") String id,
                                                   @RequestBody Allergy allergy) {
        System.out.println("Received");
        Set<Allergy> allergies = service.addAllergy(id, allergy);
        return new ResponseEntity<>(allergies, HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Set<Allergy>> getAllergies(@PathVariable("id") String id) {
        Set<Allergy> allergies = service.getAllergies(id);
        return new ResponseEntity<>(allergies, HttpStatus.OK);
    }

    @PutMapping("/update/{id}/{title}")
    public ResponseEntity<Set<Allergy>> addAllergy(@PathVariable("id") String id,
                                                   @PathVariable("title") String title,
                                                   @RequestBody Allergy allergy) {
        Set<Allergy> allergies = service.updateAllergy(id, title, allergy);
        return new ResponseEntity<>(allergies, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}/{title}")
    public ResponseEntity<Set<Allergy>> deleteAllergy(@PathVariable("id") String id,
                                                      @PathVariable("title") String title) {
        Set<Allergy> allergies = service.deleteAllergy(id, title);
        return new ResponseEntity<>(allergies, HttpStatus.OK);
    }
}
