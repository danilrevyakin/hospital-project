package com.example.hospitalproject.medicalCard.controller;

import com.example.hospitalproject.medicalCard.service.BadHabitService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("medical-card/bad-habit")
@AllArgsConstructor
public class BadHabitController {

    private final BadHabitService service;

    @PostMapping("/add/{id}/{badHabit}")
    public ResponseEntity<Set<String>> addBadHabit(@PathVariable("id") String id,
                                                   @PathVariable("badHabit") String badHabit) {
        var strings = service.addBadHabit(id, badHabit);
        return new ResponseEntity<>(strings, HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}/{badHabit}")
    public ResponseEntity<Set<String>> deleteBadHabit(@PathVariable("id") String id,
                                                      @PathVariable("badHabit") String badHabit) {
        var strings = service.deleteBadHabit(id, badHabit);
        return new ResponseEntity<>(strings, HttpStatus.OK);
    }
}
