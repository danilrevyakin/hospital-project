package com.example.hospitalproject.medicalCard;

import com.example.hospitalproject.medicalCard.model.Allergy;
import com.mongodb.client.result.UpdateResult;
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

    public void addAllergy(Long key, Allergy allergies){
        repository.findMedicalCardBySqlKey(key).ifPresent((card)->{
            Query query = new Query().addCriteria(Criteria.where(MedicalCard.field.id.name()).is(card.getId()));
            Update update = new Update().addToSet(MedicalCard.field.allergies.name(), allergies);
            UpdateResult updateResult = template.updateFirst(query, update, MedicalCard.class);
            System.out.println(query);
            System.out.println(update);
            System.out.println(updateResult);
        });
    }

    public void addAllergy(Long key, String title, String reaction){
        addAllergy(key, new Allergy(title, reaction));
    }
}
