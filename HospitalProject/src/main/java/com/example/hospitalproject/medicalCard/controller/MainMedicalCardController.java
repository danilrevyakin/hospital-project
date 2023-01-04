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
    private static final String PATIENT_ID_URL = "id";
    private static final String DOCTOR_ID_URL = "doctorId";
    private static final String ROLE_URL = "role";
    private static final String PATIENT_ROLE_URL = "patient";
    private static final String DOCTOR_ROLE_URL = "doctor";

    private static final String ALLERGIES_URL = "allergies";
    private static final String BAD_HABITS_URL = "badHabits";
    private static final String BAD_HABIT_URL = "badHabit";
    private static final String MEDICAL_RECORDS_URL = "medicalRecords";
    private static final String TITLE_URL = "allergyTitle";
    private static final String DATE_OF_URL = "recordDate";
    private static final String WRAPPER_OF_DATA = "wrapper";
    private static final String PAIR = "pair";
    private static final String UPDATED_RECORD = "updatedRecord";
    private static final String UPDATED_ALLERGY = "updatedAllergy";

    private final MedicalCardController cardController;
    private final AllergyController allergyController;
    private final MedicalRecordController recordController;
    private final BadHabitController badHabitController;

    private record ModelAttributeWrapper<V>(V value) {
    }

    private record Pair<V, T>(V first, T second) {
    }

    @GetMapping("/get/doctor/" + "{" + PATIENT_ID_URL + "}/{" + PATIENT_URL + "}/{" + DOCTOR_ID_URL + "}/{" + DOCTOR_URL + "}")
    public ModelAndView getMedicalCardPageAsDoctor(@PathVariable(PATIENT_ID_URL) String id,
                                                   @PathVariable(PATIENT_URL) String patientName,
                                                   @PathVariable(value = DOCTOR_ID_URL) String doctorId,
                                                   @PathVariable(DOCTOR_URL) String doctorName) {
        return getHome(id, patientName, doctorId, doctorName);
    }

    @GetMapping("/get/patient/" + "{" + PATIENT_ID_URL + "}/{" + PATIENT_URL + "}")//id is the same as SQL id of Patient
    public ModelAndView getMedicalCardPageAsPatient(@PathVariable(PATIENT_ID_URL) String id,
                                                    @PathVariable(PATIENT_URL) String patientName) {
        return getHome(id, patientName, null, null);
    }

    @PostMapping({"/get/param/", "/", ""})//id is the same as SQL id of Patient
    public ModelAndView getMedicalCardPageByRequestParameters(@RequestParam(value = PATIENT_ID_URL) String id,
                                                              @RequestParam(value = PATIENT_URL) String patientName,
                                                              @RequestParam(value = DOCTOR_ID_URL, required = false) String doctorId,
                                                              @RequestParam(value = DOCTOR_URL, required = false) String doctorName) {
        return getHome(id, patientName, doctorId, doctorName);
    }

    @PostMapping("bad-habit/add/")
    public ModelAndView addBadHabit(@RequestParam(PATIENT_ID_URL) String id,
                                    @RequestParam(PATIENT_URL) String patientName,
                                    @RequestParam(value = DOCTOR_ID_URL, required = false) String doctorId,
                                    @RequestParam(value = DOCTOR_URL, required = false) String doctorName,
                                    @ModelAttribute(WRAPPER_OF_DATA) ModelAttributeWrapper<String> badHabit) {
        System.out.printf("it is '%s'", doctorId);
        ResponseEntity<Set<String>> response = badHabitController.addBadHabit(id, badHabit.value);
        return getHome(id, patientName, doctorId, doctorName, response);
    }

    @PostMapping("bad-habit/update/")
    public ModelAndView updateBadHabit(@RequestParam(PATIENT_ID_URL) String id,
                                       @RequestParam(PATIENT_URL) String patientName,
                                       @RequestParam(value = DOCTOR_ID_URL) String doctorId,
                                       @RequestParam(DOCTOR_URL) String doctorName,
                                       @ModelAttribute(PAIR) Pair<String, String> update) {
        String oldBadHabit, newBadHabit;
        oldBadHabit = update.first;
        newBadHabit = update.second;
        ResponseEntity<Set<String>> response = badHabitController
                .updateBadHabit(id, oldBadHabit, newBadHabit);
        return getHome(id, patientName, doctorId, doctorName, response);
    }

    @PostMapping("bad-habit/delete/")
    public ModelAndView deleteBadHabit(@RequestParam(PATIENT_ID_URL) String id,
                                       @RequestParam(PATIENT_URL) String patientName,
                                       @RequestParam(value = DOCTOR_ID_URL) String doctorId,
                                       @RequestParam(DOCTOR_URL) String doctorName,
                                       @RequestParam(BAD_HABIT_URL) String badHabit) {
        ResponseEntity<Set<String>> response = badHabitController.deleteBadHabit(id, badHabit);
        return getHome(id, patientName, doctorId, doctorName, response);
    }

    @PostMapping("allergy/update/")
    public ModelAndView updateAllergy(@ModelAttribute Allergy allergy,
                                      @ModelAttribute(WRAPPER_OF_DATA) ModelAttributeWrapper<String> wrapper,
                                      @RequestParam(PATIENT_ID_URL) String id,
                                      @RequestParam(PATIENT_URL) String patientName,
                                      @RequestParam(value = DOCTOR_ID_URL) String doctorId,
                                      @RequestParam(DOCTOR_URL) String doctorName) {
        ResponseEntity<Set<Allergy>> response = allergyController.updateAllergy(id, wrapper.value, allergy);
        return getHome(id, patientName, doctorId, doctorName, response);
    }

    @PostMapping("allergy/delete/")
    public ModelAndView deleteAllergy(@RequestParam(PATIENT_ID_URL) String id,
                                      @RequestParam(PATIENT_URL) String patientName,
                                      @RequestParam(value = DOCTOR_ID_URL) String doctorId,
                                      @RequestParam(DOCTOR_URL) String doctorName,
                                      @RequestParam(TITLE_URL) String title) {
        ResponseEntity<Set<Allergy>> response = allergyController.deleteAllergy(id, title);
        return getHome(id, patientName, doctorId, doctorName, response);
    }

    @PostMapping("allergy/add/")
    public ModelAndView addAllergy(@RequestParam(PATIENT_ID_URL) String id,
                                   @RequestParam(PATIENT_URL) String patientName,
                                   @RequestParam(value = DOCTOR_ID_URL, required = false) String doctorId,
                                   @RequestParam(DOCTOR_URL) String doctorName,
                                   @ModelAttribute(UPDATED_ALLERGY) Allergy allergy) {
        ResponseEntity<Set<Allergy>> response = allergyController.addAllergy(id, allergy);
        return getHome(id, patientName, doctorId, doctorName, response);
    }

    @PostMapping("record/update/")
    public ModelAndView updateRecord(@ModelAttribute(UPDATED_RECORD) MedicalRecord newRecord,
                                     @RequestParam(PATIENT_ID_URL) String id,
                                     @RequestParam(PATIENT_URL) String patientName,
                                     @RequestParam(value = DOCTOR_ID_URL) String doctorId,
                                     @RequestParam(DOCTOR_URL) String doctorName) {
        ResponseEntity<List<MedicalRecord>> response = recordController.updateMedicalRecord(id, doctorName, newRecord);
        return getHome(id, patientName, doctorId, doctorName, response);
    }

    @PostMapping("record/add/")
    public ModelAndView addRecord(@ModelAttribute(UPDATED_RECORD) MedicalRecord newRecord,
                                  @RequestParam(PATIENT_ID_URL) String id,
                                  @RequestParam(PATIENT_URL) String patientName,
                                  @RequestParam(value = DOCTOR_ID_URL) String doctorId,
                                  @RequestParam(DOCTOR_URL) String doctorName) {
        ResponseEntity<List<MedicalRecord>> response = recordController.addMedicalRecord(id, doctorName, newRecord);
        return getHome(id, patientName, doctorId, doctorName, response);
    }

    @PostMapping("record/delete/")
    public ModelAndView deleteRecord(@RequestParam(PATIENT_ID_URL) String id,
                                     @RequestParam(PATIENT_URL) String patientName,
                                     @RequestParam(value = DOCTOR_ID_URL) String doctorId,
                                     @RequestParam(DOCTOR_URL) String doctorName,
                                     @RequestParam(DATE_OF_URL) String date) {
        ResponseEntity<List<MedicalRecord>> response = recordController.deleteMedicalRecord(id, date, doctorName);
        return getHome(id, patientName, doctorId, doctorName, response);
    }

    private ModelAndView getHome(String id, String patientName, String doctorId, String doctorName, ResponseEntity<?> response) {
        ModelAndView home = getHome(id, patientName, doctorId, doctorName);
        home.setStatus(response.getStatusCode());
        return home;
    }

    private ModelAndView getHome(String id, String patientName, String doctorId, String doctorName) {
        ResponseEntity<MedicalCard> response = cardController.getMedicalCard(id);
        MedicalCard medicalCard = response.getBody();
        assert medicalCard != null;
        final String path = $ + "medicalCard" + $ + "medicalCard";
        ModelAndView model = new ModelAndView(path);
        model.addObject(PATIENT_ID_URL, id);
        model.addObject(PATIENT_URL, patientName);
        model.addObject(DOCTOR_URL, doctorName);
        model.addObject(WRAPPER_OF_DATA, new ModelAttributeWrapper<>("wrapper value"));
        model.addObject(PAIR, new Pair<>("old", "new"));
        Set<Allergy> allergies = medicalCard.getAllergies();
        model.addObject(ALLERGIES_URL, allergies);
        model.addObject(BAD_HABITS_URL, medicalCard.getBadHabits());
        model.addObject(MEDICAL_RECORDS_URL, medicalCard.getRecords());
        model.addObject(UPDATED_ALLERGY, new Allergy());
        model.addObject(UPDATED_RECORD, new MedicalRecord());
        model.setStatus(response.getStatusCode());
        model.addObject(DOCTOR_ID_URL, doctorId);
        if (doctorId == null || doctorId.length() < 1) {
            return model.addObject(ROLE_URL, PATIENT_ROLE_URL);
        }
        return model.addObject(ROLE_URL, DOCTOR_ROLE_URL);
    }
}
