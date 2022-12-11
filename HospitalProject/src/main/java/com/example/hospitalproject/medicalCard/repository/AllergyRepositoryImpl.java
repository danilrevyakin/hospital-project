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
import static com.example.hospitalproject.medicalCard.repository.CustomMedicalCardRepositoryImpl.getQueryById;

@Repository
@AllArgsConstructor
public class AllergyRepositoryImpl implements AllergyRepository {

    private final MongoTemplate template;

    private static final String allergies = "allergies";

    public enum AllergyFields {
        title(allergies + ".title", allergies + ".$title"),
        reaction(allergies + ".reaction", allergies + ".$reaction");

        public final String path;
        public final String path$;
        AllergyFields(String path, String path$) {
            this.path = path;
            this.path$ = path$;
        }
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
        Update update = new Update().set(MedicalCard.field.allergies.name() + ".$", allergy);
        template.updateFirst(query, update, MedicalCard.class);
    }

    //db.medicalCard.updateOne({_id: "id"},{$pull: {allergies:{title: "title"}}})
    @Override
    public void deleteAllergy(String id, String title) {
        Query query = getQueryById(id);
        Update update = new Update().pull(MedicalCard.field.allergies.name(),
                Query.query(Criteria.where(AllergyFields.title.name()).is(title)));
        System.out.println(update);
        UpdateResult updateResult = template.updateFirst(query, update, MedicalCard.class);
        System.out.println(updateResult);
    }
}
