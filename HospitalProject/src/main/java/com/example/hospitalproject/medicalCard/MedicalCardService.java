package com.example.hospitalproject.medicalCard;

import com.example.hospitalproject.medicalCard.model.Allergy;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class MedicalCardService {

    private final MedicalCardRepository repository;
    private final MongoTemplate template;

    public List<MedicalCard> getAllCards() {
        return repository.findAll();
    }

    public MedicalCard getMedicalCardByKey(Long key) {//get saved or create new
        return repository.findMedicalCardBySqlKey(key).orElseGet(()->{
            MedicalCard card = new MedicalCard(key, null, null, null, LocalDate.now());
            repository.save(card);
            return card;
        });
    }

    public void addAllergy(Long key, Allergy... allergies){
        repository.findMedicalCardBySqlKey(key).ifPresent((card)->{
            Query query = new Query().addCriteria(Criteria.where(MedicalCard.keys.id.name()).is(card.getId()));
            Update update = new Update().push(MedicalCard.keys.allergies.name(), allergies);
            template.updateFirst(query, update, Allergy.class);
        });
    }

    public void addAllergy(Long key, String title, String reaction){
        addAllergy(key, new Allergy(title, reaction));
    }
}
