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

    @GetMapping("/all/{id}")
    public ResponseEntity<Set<String>> getBadHabit(@PathVariable("id") String id){
        var strings = service.getAllBadHabits(id);
        return new ResponseEntity<>(strings, HttpStatus.OK);
    }

    @PutMapping("/add/{id}/{badHabit}")
    public ResponseEntity<Set<String>> addBadHabit(@PathVariable("id") String id,
                                                   @PathVariable("badHabit") String badHabit) {
        var strings = service.addBadHabit(id, badHabit);
        return new ResponseEntity<>(strings, HttpStatus.OK);
    }

    @PutMapping("/update/{id}/{badHabit}/{newBadHabit}")
    public ResponseEntity<Set<String>> updateBadHabit(@PathVariable("id") String id,
                                                   @PathVariable("badHabit") String badHabit,
                                                   @PathVariable("newBadHabit") String newBadHabit) {
        var strings = service.updateBadHabit(id, badHabit, newBadHabit);
        return new ResponseEntity<>(strings, HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}/{badHabit}")
    public ResponseEntity<Set<String>> deleteBadHabit(@PathVariable("id") String id,
                                                      @PathVariable("badHabit") String badHabit) {
        var strings = service.deleteBadHabit(id, badHabit);
        return new ResponseEntity<>(strings, HttpStatus.OK);
    }
}
