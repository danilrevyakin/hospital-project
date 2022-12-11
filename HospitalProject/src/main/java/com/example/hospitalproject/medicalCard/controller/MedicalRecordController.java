package com.example.hospitalproject.medicalCard.controller;

import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import com.example.hospitalproject.medicalCard.service.MedicalCardService;
import com.example.hospitalproject.medicalCard.service.MedicalRecordService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medical-card/record")
@AllArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService service;


    @PostMapping("/add/{id}")
    public ResponseEntity<List<MedicalRecord>> addMedicalRecord(@PathVariable("id") String id,
                                                                @RequestBody MedicalRecord record) {
        List<MedicalRecord> medicalRecords = service.addMedicalRecord(id, record);
        return new ResponseEntity<>(medicalRecords, HttpStatus.OK);
    }
}
