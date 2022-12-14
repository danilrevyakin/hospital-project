package com.example.hospitalproject.medicalCard.service;

import com.example.hospitalproject.medicalCard.exception.MedicalCardNotFoundException;
import com.example.hospitalproject.medicalCard.model.Allergy;
import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.repository.MedicalCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class AllergyService {

    private final MedicalCardRepository repository;

    public Set<Allergy> getAllergies(String id) {
        if (repository.existsById(id)) {
            return repository.getAllAllergiesById(id);
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
            repository.addAllergy(id, allergy);
            return repository.getAllAllergiesById(id);
        }
        throw new MedicalCardNotFoundException();
    }

    public Set<Allergy> updateAllergy(String id, String title, Allergy allergy) {
        return modifyAllergyOperation(id, title,
                () -> repository.updateAllergy(id, title, allergy));
    }

    public Set<Allergy> deleteAllergy(String id, String title) {
        return modifyAllergyOperation(id, title, () -> repository.deleteAllergy(id, title));
    }

    private Set<Allergy> modifyAllergyOperation(String id, String title, Runnable runner) {
        Optional<MedicalCard> cardOptional = repository.findById(id);
        if (cardOptional.isPresent()) {
            if (!cardOptional.get().getAllergies().contains(new Allergy(title, null))) {
                throw new IllegalStateException("There is no " + title + " allergy in " + id + " card");
            }
            runner.run();
            return repository.getAllAllergiesById(id);
        }
        throw new MedicalCardNotFoundException();
    }
}
