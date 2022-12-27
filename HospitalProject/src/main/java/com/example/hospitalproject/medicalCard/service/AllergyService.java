package com.example.hospitalproject.medicalCard.service;

import com.example.hospitalproject.medicalCard.exception.IllegalAllergyException;
import com.example.hospitalproject.medicalCard.exception.MedicalCardNotFoundException;
import com.example.hospitalproject.medicalCard.model.Allergy;
import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.repository.AllergyRepository;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import com.mongodb.Function;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class AllergyService {

    private final MedicalCardRepository repository;
    private final AllergyRepository allergyRepository;
    private static final Function<String, String> f = (s) -> s.trim().replaceAll(" +", " ").toLowerCase();
    private static final int MINIMUM_TITLE_LENGTH = 1;

    public Set<Allergy> getAllergies(String id) {
        if (repository.existsById(id)) {
            return allergyRepository.getAllAllergiesById(id);
        }
        throw new MedicalCardNotFoundException();
    }

    public Set<Allergy> addAllergy(String id, Allergy allergy) {
        Optional<MedicalCard> cardOptional = repository.findById(id);
        if (cardOptional.isPresent()) {
            Set<Allergy> allergies = cardOptional.get().getAllergies();
            if (allergies != null && allergies.contains(allergy)) {
                throw new IllegalStateException("There is already present " + allergy);
            }
            formatAllergy(allergy);
            allergyRepository.addAllergy(id, allergy);
            return allergyRepository.getAllAllergiesById(id);
        }
        throw new MedicalCardNotFoundException();
    }

    private void formatAllergy(Allergy allergy){
        String title = allergy.getTitle();
        title = checkTitle(title);
        allergy.setTitle(title);
        String reaction = allergy.getReaction();
        if(reaction != null){
            allergy.setReaction(f.apply(reaction));
        }
    }

    private String checkTitle(String title){
        if(title == null){
            throw new IllegalAllergyException("Title of allergy can not be null");
        }
        title = f.apply(title);
        if(title.length() < MINIMUM_TITLE_LENGTH){
            String message = "Length of allergy title can not be less than " + MINIMUM_TITLE_LENGTH;
            throw new IllegalAllergyException(message);
        }
        return title;
    }

    public Set<Allergy> updateAllergy(String id, String title, Allergy allergy) {
        formatAllergy(allergy);
        return modifyAllergyOperation(id, title,
                () -> allergyRepository.updateAllergy(id, title, allergy));
    }

    public Set<Allergy> deleteAllergy(String id, String title) {
        return modifyAllergyOperation(id, title, () -> allergyRepository.deleteAllergy(id, title));
    }

    private Set<Allergy> modifyAllergyOperation(String id, String title, Runnable runner) {
        checkTitle(title);
        Optional<MedicalCard> cardOptional = repository.findById(id);
        if (cardOptional.isPresent()) {
            if (!cardOptional.get().getAllergies().contains(new Allergy(title, null))) {
                throw new IllegalAllergyException("There is no " + title + " allergy in " + id + " card");
            }
            runner.run();
            return allergyRepository.getAllAllergiesById(id);
        }
        throw new MedicalCardNotFoundException();
    }
}
