package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.Allergy;
import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static com.example.hospitalproject.medicalCard.repository.MedicalRecordRepositoryImpl.getQueryById;

@Repository
@AllArgsConstructor
public class AllergyRepositoryImpl implements AllergyRepository {

    private final MongoTemplate template;
    private final Logger logger = Logger.getLogger(this.getClass().getName());


    private static final String allergies = "allergies";

    public enum AllergyFields {
        title,
        reaction;

        public final String path;
        public final String path$;

        AllergyFields() {
            this.path = allergies + "." + this.name();
            this.path$ = allergies + ".$" + this.name();
        }
    }

    @Override
    public Set<Allergy> getAllAllergiesById(String id) {
        Query query = getQueryById(id);
        query.fields().include(MedicalCard.field.allergies.name())
                .exclude(MedicalCard.field.id.n);
        logger.info(query.toString());
        List<MedicalCard> objects = template.find(query, MedicalCard.class);
        logger.info(objects.toString());
        if (objects.size() < 1) {
            return Set.of();
        } else if (objects.get(0).getAllergies() == null || objects.get(0).getAllergies().size() < 1) {
            return Set.of();
        }
        return objects.get(0).getAllergies();
    }

    @Override
    public void addAllergy(String id, Allergy allergy) {
        Query query = getQueryById(id);
        Update update = new Update().addToSet(MedicalCard.field.allergies.name(), allergy);
        template.updateFirst(query, update, MedicalCard.class);
    }

    @Override
    public void updateAllergy(String id, String title, Allergy allergy) {
        Query query = getQueryById(id).
                addCriteria(Criteria.where(AllergyFields.title.path).is(title));
        Update update = new Update().set(MedicalCard.field.allergies.nameDot$, allergy);
        template.updateFirst(query, update, MedicalCard.class);
    }

    //db.medicalCard.updateOne({_id: "id"},{$pull: {allergies:{title: "title"}}})
    @Override
    public void deleteAllergy(String id, String title) {
        Query query = getQueryById(id);
        Update update = new Update().pull(MedicalCard.field.allergies.name(),
                Query.query(Criteria.where(AllergyFields.title.name()).is(title)));
        logger.info(update.toString());
        UpdateResult updateResult = template.updateFirst(query, update, MedicalCard.class);
        logger.info(updateResult.toString());
    }
}
