package com.example.hospitalproject.medicalCard.controller;

import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import com.example.hospitalproject.medicalCard.service.MedicalRecordService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("medical-card/record")
@AllArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService service;

    @GetMapping("/get/{id}/{date}")
    public ResponseEntity<MedicalRecord> addMedicalRecord(@PathVariable("id") String id,
                                                          @PathVariable("date") String date) {
        MedicalRecord record = service.getMedicalRecord(id, LocalDateTime.parse(date));
        return new ResponseEntity<>(record, HttpStatus.OK);
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<List<MedicalRecord>> addMedicalRecord(@PathVariable("id") String id,
                                                                @RequestBody MedicalRecord record) {
        List<MedicalRecord> medicalRecords = service.addMedicalRecord(id, record);
        return new ResponseEntity<>(medicalRecords, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<List<MedicalRecord>> updateMedicalRecord(@PathVariable("id") String id,
                                                                   @RequestBody MedicalRecord record) {
        List<MedicalRecord> medicalRecords = service.updateMedicalRecord(id, record);
        return new ResponseEntity<>(medicalRecords, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}/{date}/{doctor}")
    public ResponseEntity<List<MedicalRecord>> deleteMedicalRecord(@PathVariable("id") String id,
                                                                   @PathVariable("date") String date,
                                                                   @PathVariable("doctor") String doctor) {
        List<MedicalRecord> records = service.deleteMedicalRecord(id, LocalDateTime.parse(date), doctor);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }
}
