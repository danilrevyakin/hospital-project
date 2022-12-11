package com.example.hospitalproject.medicalCard;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import com.example.hospitalproject.medicalCard.service.MedicalCardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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



    @PostMapping("/add-bad-habit/{id}/{badHabit}")
    public ResponseEntity<Set<String>> addBadHabit(@PathVariable("id") String id,
                                                   @PathVariable("badHabit") String badHabit) {
        var strings = service.addBadHabit(id, badHabit);
        return new ResponseEntity<>(strings, HttpStatus.OK);
    }

    @PostMapping("/add-medical-record/{id}/{info}/{symptoms}/{treatment}/{doctor}")
//    http://localhost:8080/medical-card/add-medical-record/1/info/symptoms/treatment/doctor
    public ResponseEntity<List<MedicalRecord>>  addMedicalRecord(@PathVariable("id") String id,
                                   @PathVariable("info") String info,
                                   @PathVariable("symptoms") String symptoms,
                                   @PathVariable("treatment") String treatment,
                                   @PathVariable("doctor") String doctor) {
        List<MedicalRecord> medicalRecords = service.addMedicalRecord(id, info, symptoms, treatment, doctor);
        return new ResponseEntity<>(medicalRecords, HttpStatus.OK);
    }

    @PostMapping("/add-medical-record/{id}")
//    http://localhost:8080/medical-card/add-medical-record/1/...
    public ResponseEntity<List<MedicalRecord>>  addMedicalRecord(@PathVariable("id") String id,
                                                                 @RequestBody MedicalRecord record) {
        List<MedicalRecord> medicalRecords = service.addMedicalRecord(id, record);
        return new ResponseEntity<>(medicalRecords, HttpStatus.OK);
    }
}
