package com.example.hospitalproject.medicalCard.service.implementation;

import com.example.hospitalproject.medicalCard.exception.IllegalAllergyException;
import com.example.hospitalproject.medicalCard.exception.MedicalCardNotFoundException;
import com.example.hospitalproject.medicalCard.model.Allergy;
import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.repository.AllergyRepository;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import com.example.hospitalproject.medicalCard.service.AllergyService;
import com.mongodb.Function;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class AllergyServiceImpl implements AllergyService {

    private final MedicalCardRepository repository;
    private final AllergyRepository allergyRepository;
    private static final Function<String, String> f = (s) -> s.trim().replaceAll(" +", " ").toLowerCase();
    private static final int MINIMUM_TITLE_LENGTH = 1;

    @Override
    public Set<Allergy> getAll(String id) {
        if (repository.existsById(id)) {
            return allergyRepository.getAllAllergiesById(id);
        }
        throw new MedicalCardNotFoundException();
    }

    @Override
    public Set<Allergy> add(String id, Allergy allergy) {
        Optional<MedicalCard> cardOptional = repository.findById(id);
        if (cardOptional.isPresent()) {
            Set<Allergy> allergies = cardOptional.get().getAllergies();
            formatAllergy(allergy);
            if (allergies != null && allergies.contains(allergy)) {
                throw new IllegalStateException("There is already present " + allergy);
            }
            allergyRepository.addAllergy(id, allergy);
            return allergyRepository.getAllAllergiesById(id);
        }
        throw new MedicalCardNotFoundException();
    }

    @Override
    public Set<Allergy> update(String id, String title, Allergy allergy) {
        formatAllergy(allergy);
        return modifyAllergyOperation(id, title,
                () -> allergyRepository.updateAllergy(id, title, allergy));
    }

    @Override
    public Set<Allergy> delete(String id, String title) {
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
}
