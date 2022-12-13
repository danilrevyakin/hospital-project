package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

    //    db.medicalCard.find({_id: "1"},{"records$": {"records.date": "2022-12-11T23:15:15.522"}})
    @Override
    public Optional<MedicalRecord> getMedicalRecord(String id, LocalDateTime date) {
//        Query query = getQueryById(id).
//                addCriteria(Criteria.where(RecordFields.date.path$).is(date.toString()));
//        logger.info(query.toString());
//        MedicalRecord one = template.findOne(query, MedicalRecord.class);
//        logger.info(template.findOne(query, LocalDateTime.class).toString());

        return repository.getMedicalRecord(id, date);
    }


    @Override
    public void addMedicalRecord(String id, MedicalRecord record) {
        Query query = getQueryById(id);
        Update update = new Update().push(MedicalCard.field.records.name(), record);
        template.updateFirst(query, update, MedicalCard.class);
    }

    @Override
    public void updateMedicalRecord(String id, MedicalRecord newRecord) {
        Query query = getQueryById(id).
                addCriteria(Criteria.where(RecordFields.date.path).is(newRecord.getDate()));
        Update update = new Update().set(MedicalCard.field.records.nameDot$, newRecord);
        logger.info(query + "\n" + update);
        UpdateResult updateResult = template.updateFirst(query, update, MedicalCard.class);
        logger.info(updateResult.toString());
    }

    @Override
    public void deleteMedicalRecord(String id, LocalDateTime dateOfOldRecord) {

    }

    protected static Query getQueryById(String id) {
        return new Query().addCriteria(Criteria.where("_" + MedicalCard.field.id.name()).is(id));
    }


}
