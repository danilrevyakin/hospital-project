package com.example.hospitalproject.medicalCard.controller;

import com.example.hospitalproject.medicalCard.model.Allergy;
import com.example.hospitalproject.medicalCard.model.MedicalCard;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.Arrays;
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
    private static final String TITLE_URL = "title";
    private static final String DEFAULT_DATA_URL = "{" + ID_URL + "}/{" + PATIENT_URL + "}/{" + DOCTOR_URL + "}";

    private final MedicalCardController cardController;
    private final AllergyController allergyController;

    private record RequestData(String id, String doctorName, String patientName, String data) {
            public static final String URL = "requestBody";
    }

    @GetMapping("/get/" + DEFAULT_DATA_URL)//id is the same as SQL id of Patient
    public ModelAndView getMedicalCardPage(@PathVariable(ID_URL) String id,
                                           @PathVariable(DOCTOR_URL) String doctorName,
                                           @PathVariable(PATIENT_URL) String patientName) {
        MedicalCard medicalCard = cardController.getMedicalCard(id).getBody();
        assert medicalCard != null;
        final String path = $ + "medicalCard" + $ + "medicalCard";
        ModelAndView model = new ModelAndView(path);
        RequestData body = new RequestData(id, doctorName, patientName, null);
        model.addObject(RequestData.URL, body);
        Set<Allergy> allergies = medicalCard.getAllergies();
        System.out.println(allergies.size());
        model.addObject(ALLERGIES_URL, allergies);
        model.addObject(BAD_HABITS_URL, medicalCard.getBadHabits());
        model.addObject(MEDICAL_RECORDS_URL, medicalCard.getRecords());
        model.addObject("updatedAllergy", new Allergy());
        return model;
    }

    @PostMapping("allergy/update")//id is the same as SQL id of Patient
    public String updateAllergy(@ModelAttribute Allergy allergy,
                                @RequestBody RequestData body) {
        System.out.println("hello");
        System.out.println(allergy);
        System.out.println(body);
        allergyController.updateAllergy(body.id, body.data, allergy);
        return "redirect:/" + "get/" + dataURLFormatter(body.id, body.patientName, body.doctorName);
    }

    private static String dataURLFormatter(String... data) {
        if (data == null || data.length == 0){
            return "";
        }
        var builder = new StringBuilder();
        for(var i : data){
            builder.append('{').append(i).append('}').append('/');
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}
