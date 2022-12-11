package com.example.hospitalproject.medicalCard;

import com.example.hospitalproject.medicalCard.model.Allergy;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medical-card/allergy")
@AllArgsConstructor
public class AllergyController {

    private final MedicalCardService service;

    @PostMapping("/add/{id}")
    public ResponseEntity<List<Allergy>> addAllergy(@PathVariable("id") String id,
                                                    @RequestBody Allergy allergy ) {
        List<Allergy> allergies = service.addAllergy(id, allergy);
        return new ResponseEntity<>(allergies, HttpStatus.OK);
    }

    @PutMapping("/update/{id}/{title}")
    public ResponseEntity<List<Allergy>> addAllergy(@PathVariable("id") String id,
                                                    @PathVariable("title") String title,
                                                    @RequestBody Allergy allergy ) {
        List<Allergy> allergies = service.updateAllergy(id, title, allergy);
        return new ResponseEntity<>(allergies, HttpStatus.OK);
    }
}
