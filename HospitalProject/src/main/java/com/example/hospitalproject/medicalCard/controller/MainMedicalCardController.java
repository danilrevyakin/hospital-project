package com.example.hospitalproject.medicalCard.controller;

import com.example.hospitalproject.medicalCard.model.Allergy;
import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("medical-card/page")
@AllArgsConstructor
public class MainMedicalCardController {
    private static final String $ = File.separator;
    private static final String DOCTOR_URL = "doctorName";
    private static final String PATIENT_URL = "patientName";
    private static final String ID_URL = "id";
    private static final String ALLERGIES_URL = "allergies";
    private static final String BAD_HABITS_URL = "badHabits";
    private static final String MEDICAL_RECORDS_URL = "medicalRecords";
    private static final String TITLE_URL = "allergyTitle";
    private static final String DATE_OF_URL = "recordDate";
    private static final String DEFAULT_DATA_URL = "{" + ID_URL + "}/{" + PATIENT_URL + "}/{" + DOCTOR_URL + "}";
    private static final String WRAPPER_OF_DATA = "wrapper";
    private static final String UPDATED_RECORD = "updatedRecord";
    private static final String UPDATED_ALLERGY = "updatedAllergy";

    private final MedicalCardController cardController;
    private final AllergyController allergyController;
    private final MedicalRecordController recordController;

    private record ModelAttributeWrapper<V>(V value) {
    }

    @GetMapping("/get/" + DEFAULT_DATA_URL)//id is the same as SQL id of Patient
    public ModelAndView getMedicalCardPage(@PathVariable(ID_URL) String id,
                                           @PathVariable(DOCTOR_URL) String doctorName,
                                           @PathVariable(PATIENT_URL) String patientName) {
        return getHome(id, patientName, doctorName);
    }

    @PostMapping({"/get/param/", "/", ""})//id is the same as SQL id of Patient
    public ModelAndView getMedicalCardPageByRequestParameters(@RequestParam(value = ID_URL) String id,
                                                              @RequestParam(value = DOCTOR_URL) String doctorName,
                                                              @RequestParam(value = PATIENT_URL) String patientName) {
        return getHome(id, patientName, doctorName);
    }

    @PostMapping("allergy/update/")
    public ModelAndView updateAllergy(@ModelAttribute Allergy allergy,
                                      @ModelAttribute(WRAPPER_OF_DATA) ModelAttributeWrapper<String> wrapper,
                                      @RequestParam(ID_URL) String id,
                                      @RequestParam(DOCTOR_URL) String doctorName,
                                      @RequestParam(PATIENT_URL) String patientName) {
        ResponseEntity<Set<Allergy>> response = allergyController.updateAllergy(id, wrapper.value, allergy);
        return getHome(id, patientName, doctorName, response);
    }

    @PostMapping("allergy/delete/")
    public ModelAndView deleteAllergy(@RequestParam(ID_URL) String id,
                                      @RequestParam(DOCTOR_URL) String doctorName,
                                      @RequestParam(PATIENT_URL) String patientName,
                                      @RequestParam(TITLE_URL) String title) {
        ResponseEntity<Set<Allergy>> response = allergyController.deleteAllergy(id, title);
        return getHome(id, patientName, doctorName, response);
    }

    @PostMapping("allergy/add/")
    public ModelAndView addAllergy(@RequestParam(ID_URL) String id,
                                   @RequestParam(DOCTOR_URL) String doctorName,
                                   @RequestParam(PATIENT_URL) String patientName,
                                   @ModelAttribute(UPDATED_ALLERGY) Allergy allergy) {
        ResponseEntity<Set<Allergy>> response = allergyController.addAllergy(id, allergy);
        return getHome(id, patientName, doctorName, response);
    }

    @PostMapping("record/update/")
    public ModelAndView updateRecord(@ModelAttribute(UPDATED_RECORD) MedicalRecord newRecord,
                                     @RequestParam(ID_URL) String id,
                                     @RequestParam(DOCTOR_URL) String doctorName,
                                     @RequestParam(PATIENT_URL) String patientName) {
        ResponseEntity<List<MedicalRecord>> response = recordController.updateMedicalRecord(id, doctorName, newRecord);
        return getHome(id, patientName, doctorName, response);
    }

    @PostMapping("record/add/")
    public ModelAndView addRecord(@ModelAttribute(UPDATED_RECORD) MedicalRecord newRecord,
                                  @RequestParam(ID_URL) String id,
                                  @RequestParam(DOCTOR_URL) String doctorName,
                                  @RequestParam(PATIENT_URL) String patientName) {
        ResponseEntity<List<MedicalRecord>> response = recordController.addMedicalRecord(id, doctorName, newRecord);
        return getHome(id, patientName, doctorName, response);
    }

    @PostMapping("record/delete/")
    public ModelAndView deleteRecord(@RequestParam(ID_URL) String id,
                                     @RequestParam(DOCTOR_URL) String doctorName,
                                     @RequestParam(PATIENT_URL) String patientName,
                                     @RequestParam(DATE_OF_URL) String date) {
        ResponseEntity<List<MedicalRecord>> response = recordController.deleteMedicalRecord(id, date, doctorName);
        return getHome(id, patientName, doctorName, response);
    }

    private ModelAndView getHome(String id, String patientName, String doctorName, ResponseEntity<?> response) {
        ModelAndView home = getHome(id, patientName, doctorName);
        home.setStatus(response.getStatusCode());
        return home;
    }

    private ModelAndView getHome(String id, String patientName, String doctorName) {
        ResponseEntity<MedicalCard> response = cardController.getMedicalCard(id);
        MedicalCard medicalCard = response.getBody();
        assert medicalCard != null;
        final String path = $ + "medicalCard" + $ + "medicalCard";
        ModelAndView model = new ModelAndView(path);
        model.addObject(ID_URL, id);
        model.addObject(PATIENT_URL, patientName);
        model.addObject(DOCTOR_URL, doctorName);
        model.addObject(WRAPPER_OF_DATA, new ModelAttributeWrapper<>("default"));
        Set<Allergy> allergies = medicalCard.getAllergies();
        model.addObject(ALLERGIES_URL, allergies);
        model.addObject(BAD_HABITS_URL, medicalCard.getBadHabits());
        model.addObject(MEDICAL_RECORDS_URL, medicalCard.getRecords());
        model.addObject(UPDATED_ALLERGY, new Allergy());
        model.addObject(UPDATED_RECORD, new MedicalRecord());
        model.setStatus(response.getStatusCode());
        return model;
    }
}
