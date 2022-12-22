package com.example.hospitalproject.medicalCard.controller;

import com.example.hospitalproject.medicalCard.model.Allergy;
import com.example.hospitalproject.medicalCard.model.MedicalCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.Set;

@RestController
@RequestMapping("medical-card/page")
@AllArgsConstructor
public class MainMedicalCardController {
    private static final String $ = File.separator;
    private static final String DOCTOR_URL = "doctorName";
    private static final String PATIENT_URL = "patientName";
    private static final String ID_URL = "id";
    private static final String MEDICAL_CARD_URL = "medicalCard";
    private static final String ALLERGIES_URL = "allergies";
    private static final String BAD_HABITS_URL = "badHabits";
    private static final String MEDICAL_RECORDS_URL = "medicalRecords";
    private static final String TITLE_URL = "allergyTitle";
    private static final String DEFAULT_DATA_URL = "{" + ID_URL + "}/{" + PATIENT_URL + "}/{" + DOCTOR_URL + "}";
    private static final String WRAPPER_OF_DATA = "wrapper";
    private final MedicalCardController cardController;
    private final AllergyController allergyController;

    @Data
    private class ModelAttributeWrapper<V>{
        private final V value;
    }

    @GetMapping("/get/" + DEFAULT_DATA_URL)//id is the same as SQL id of Patient
    public ModelAndView getMedicalCardPage(@PathVariable(ID_URL) String id,
                                           @PathVariable(DOCTOR_URL) String doctorName,
                                           @PathVariable(PATIENT_URL) String patientName) {
        return getMedicalCardPageByRequestParameters(id, doctorName, patientName);
    }

    @GetMapping("/get/")//id is the same as SQL id of Patient
    public ModelAndView getMedicalCardPageByRequestParameters(@RequestParam(ID_URL) String id,
                                                              @RequestParam(DOCTOR_URL) String doctorName,
                                                              @RequestParam(PATIENT_URL) String patientName) {
        MedicalCard medicalCard = cardController.getMedicalCard(id).getBody();
        assert medicalCard != null;
        final String path = $ + "medicalCard" + $ + "medicalCard";
        ModelAndView model = new ModelAndView(path);
        model.addObject(ID_URL, id);
        model.addObject(PATIENT_URL, patientName);
        model.addObject(DOCTOR_URL, doctorName);
        model.addObject(WRAPPER_OF_DATA, new ModelAttributeWrapper<>("default"));
        Set<Allergy> allergies = medicalCard.getAllergies();
        System.out.println(allergies.size());
        model.addObject(ALLERGIES_URL, allergies);
        model.addObject(BAD_HABITS_URL, medicalCard.getBadHabits());
        model.addObject(MEDICAL_RECORDS_URL, medicalCard.getRecords());
        model.addObject("updatedAllergy", new Allergy());
        return model;
    }

    @PostMapping("allergy/update/")
    public ModelAndView updateAllergy(@ModelAttribute Allergy allergy,
                                      @ModelAttribute(WRAPPER_OF_DATA) ModelAttributeWrapper<String> wrapper,
                                      @RequestParam(ID_URL) String id,
                                      @RequestParam(DOCTOR_URL) String doctorName,
                                      @RequestParam(PATIENT_URL) String patientName) {

        allergyController.updateAllergy(id, wrapper.value, allergy);
        return getMedicalCardPageByRequestParameters(id, doctorName, patientName);
    }

    @PostMapping("allergy/delete/")
    public ModelAndView deleteAllergy(@RequestParam(ID_URL) String id,
                                      @RequestParam(DOCTOR_URL) String doctorName,
                                      @RequestParam(PATIENT_URL) String patientName,
                                      @RequestParam(TITLE_URL) String title) {
        allergyController.deleteAllergy(id, title);
        return getMedicalCardPageByRequestParameters(id, doctorName, patientName);
    }

    private static String dataURLFormatter(String... data) {
        if (data == null || data.length == 0){
            return "";
        }
        var builder = new StringBuilder();
        for(var i : data){
            builder.append(i).append('/');
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}
