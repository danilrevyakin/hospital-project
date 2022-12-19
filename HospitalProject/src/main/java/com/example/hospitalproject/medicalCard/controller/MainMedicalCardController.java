package com.example.hospitalproject.medicalCard.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;

@RestController
@RequestMapping("medical-card/page")
@AllArgsConstructor
public class MainMedicalCardController {
    private static final String $ = File.separator;
    private static final String DOCTOR_URL = "doctorName";
    private static final String PATIENT_URL = "patientName";
    private static final String ID_URL = "id";
    private static final String MEDICAL_CARD = "medicalCard";

    @GetMapping("/get/{" + ID_URL + "}/{" + PATIENT_URL + "}/{" + DOCTOR_URL + "}")//id is the same as SQL id of Patient
    public ModelAndView getMedicalCardPage(@PathVariable(ID_URL) String id,
                                           @PathVariable(DOCTOR_URL) String doctorName,
                                           @PathVariable(PATIENT_URL) String patientName) {
        final String path = $ + "medicalCard" + $ + "medicalCard";
        ModelAndView model = new ModelAndView(path);
        model.addObject(PATIENT_URL, patientName);
        model.addObject(DOCTOR_URL, doctorName);
        model.addObject(ID_URL, id);
        return model;
    }
}
