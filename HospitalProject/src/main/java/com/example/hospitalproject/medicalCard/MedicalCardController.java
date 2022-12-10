package com.example.hospitalproject.medicalCard;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medical-card")
@AllArgsConstructor
public class MedicalCardController {

    private final MedicalCardService service;

    @GetMapping("/all")
    public List<MedicalCard> fetchAllMedicalCards() {
        return service.getAllCards();
    }

    @GetMapping("/get/{id}")//id is the same as SQL id of Patient
    public MedicalCard getMedicalCard(@PathVariable("id") String key) {
        return service.getMedicalCardByKey(key);
    }

    @PutMapping("/add-allergy/{id}/{title}/{reaction}")
    public String addAllergy(@PathVariable("id") String key,
                             @PathVariable("title") String title,
                             @PathVariable("reaction") String reaction) {
        service.addAllergy(key, title, reaction);
        return key + title + reaction;
    }

    @PutMapping("/add-bad-habit/{id}/{badHabit}")
    public String addBadHabit(@PathVariable("id") String key,
                              @PathVariable("badHabit") String badHabit) {
        service.addBadHabit(key, badHabit);
        return key + badHabit;
    }

    @PutMapping("/add-medical-record/{id}/{info}/{symptoms}/{treatment}/{doctor}")
//    http://localhost:8080/medical-card/add-medical-record/1/info/symptoms/treatment/doctor
    public String addMedicalRecord(@PathVariable("id") String key,
                                   @PathVariable("info") String info,
                                   @PathVariable("symptoms") String symptoms,
                                   @PathVariable("treatment") String treatment,
                                   @PathVariable("doctor") String doctor) {
        service.addMedicalRecord(key, info, symptoms, treatment, doctor);
        return String.join(" ", key, info, symptoms, treatment, doctor);
    }
}
