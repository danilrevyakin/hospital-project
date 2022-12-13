package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Logger;


@Repository
@AllArgsConstructor
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {

    private final MongoTemplate template;

    private static final String records = "records";
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public enum RecordFields{
        info,
        symptoms,
        doctor,
        date,
        edited;
        public final String path;
        public final String path$;
        RecordFields(){
            this.path = records + "." + this.name();
            this.path$ = records + ".$" + this.name();
        }
    }

    @Override
    public Optional<MedicalRecord> getMedicalRecord(String id, LocalDateTime data) {
        Query query = getQueryById(id).
                addCriteria(Criteria.where(RecordFields.date.path).is(data));
        logger.info(query.toString());
        MedicalRecord one = template.findOne(query, MedicalRecord.class);
        if(one != null){
            return Optional.of(one);
        }
        return Optional.empty();
    }

    @Override
    public void addMedicalRecord(String id, MedicalRecord record) {
        Query query = getQueryById(id);
        Update update = new Update().push(MedicalCard.field.records.name(), record);
        template.updateFirst(query, update, MedicalCard.class);
    }

    @Override
    public void updateMedicalRecord(String id, LocalDateTime dateOfOldRecord, MedicalRecord newRecord) {
        Query query = getQueryById(id).
                addCriteria(Criteria.where(RecordFields.date.path).is(dateOfOldRecord));
        Update update = new Update().set(MedicalCard.field.records.nameDot$, newRecord);
        logger.info(query + "\n" + update);
        UpdateResult updateResult = template.updateFirst(query, update, MedicalCard.class);
        logger.info(updateResult.toString());
    }

    @Override
    public void deleteMedicalRecord(String id, LocalDateTime dateOfOldRecord) {

    }

    protected static Query getQueryById(String id) {
        return new Query().addCriteria(Criteria.where(MedicalCard.field.id.name()).is(id));
    }
}
