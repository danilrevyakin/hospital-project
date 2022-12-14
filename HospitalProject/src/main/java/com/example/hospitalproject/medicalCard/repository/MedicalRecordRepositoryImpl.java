package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;


@Repository
@AllArgsConstructor
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {

    private final MongoTemplate template;
    RecordMongoRepository repository;


    private static final String records = "records";
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public enum RecordFields {
        info,
        symptoms,
        doctor,
        date,
        edited;
        public final String path;
        public final String path$;

        RecordFields() {
            this.path = records + "." + this.name();
            this.path$ = records + ".$" + this.name();
        }
    }

    //    db.medicalCard.findOne({ "_id" : "3", "records" : { "$elemMatch" : { "info" : "again"}}},
    //    { "records.$" : 1, "_id" : 0})
    @Override
    public List<MedicalRecord> getMedicalRecordsByIdAndDate(String id, LocalDateTime date) {
        Query query = getQueryById(id)
                .addCriteria(Criteria.where(MedicalCard.field.records.name())
                        .elemMatch(Criteria.where(RecordFields.date.name()).is(date)));
        Field projection = query.fields();
        projection
                .include(MedicalCard.field.records.name() + ".$")
                        .exclude(MedicalCard.field.id.n());
        logger.info(query.toString());
        List<MedicalCard> objects = template.find(query, MedicalCard.class);
        if (objects.size() < 1){
            return List.of();
        } else if (objects.get(0).getRecords().size() < 1) {
            return List.of();
        }
        return objects.get(0).getRecords();
    }

    @Data
    private class ListContainer<T>{
        public final List<T> list;
    }

    private static <T extends Enum<?> & MongoDBField>
    void excludeByCondition(T[] fields, Predicate<T> filter, Field projection) {
        for(var i: fields){
            if(filter.test(i)){
                projection.exclude(i.n());
            }
        }
    }

    @Override
    public void addMedicalRecord(String id, MedicalRecord record) {
        Query query = getQueryById(id);
        Update update = new Update().push(MedicalCard.field.records.name(), record);
        logger.info(query + "\n" + update);
        UpdateResult updateResult = template.updateFirst(query, update, MedicalCard.class);
        logger.info(updateResult.toString());
    }

    @Override
    public void updateMedicalRecord(String id, LocalDateTime date, MedicalRecord newRecord) {
        repository.updateRecord(id, date, newRecord);
    }

    @Override
    public void deleteMedicalRecord(String id, LocalDateTime dateOfOldRecord) {

    }

    protected static Query getQueryById(String id) {
        return new Query().addCriteria(Criteria.where("_" + MedicalCard.field.id.name()).is(id));
    }
}
