package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;


@Repository
@AllArgsConstructor
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {

    private final MongoTemplate template;
    private final ArrayRepository arrayRepository;


    private static final String records = "records";
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public enum RecordFields {
        info,
        symptoms,
        doctor,
        date,
        edited;
        public final String path;

        RecordFields() {
            this.path = records + "." + this.name();
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
                .exclude(MedicalCard.field.id.n);
        logger.info(query.toString());
        List<MedicalCard> objects = template.find(query, MedicalCard.class);
        if (objects.size() < 1) {
            return List.of();
        } else if (objects.get(0).getRecords().size() < 1) {
            return List.of();
        }
        return objects.get(0).getRecords();
    }

//db.medicalCard.find({_id:"3"},{records: 1, _id: 0})

    @Override
    public List<MedicalRecord> getMedicalRecordsById(String id) {
//        Query query = getQueryById(id);
//        query.fields().include(MedicalCard.field.records.name())
//                .exclude(MedicalCard.field.id.n);
//        logger.info(query.toString());
//        List<MedicalCard> objects = template.find(query, MedicalCard.class);
//        logger.info(objects.toString());
//        if (objects.size() < 1) {
//            return List.of();
//        } else if (objects.get(0).getRecords().size() < 1) {
//            return List.of();
//        }
//        return objects.get(0).getRecords();
        return arrayRepository.getArrayFromCardById(id, MedicalCard.field.records, List.of(), MedicalCard::getRecords);
    }

    @Override
    public void addMedicalRecord(String id, MedicalRecord record) {
        Query query = getQueryById(id);
        Update update = new Update().push(MedicalCard.field.records.name(), record);
        logger.info(query + "\n" + update);
        UpdateResult updateResult = template.updateFirst(query, update, MedicalCard.class);
        logger.info(updateResult.toString());
    }

    //    db.medicalCard.findAndModify({query: { "_id" : "3", "records" : { "$elemMatch" : { "info" : "now...."}}},
//        update: {$set: {"records.$": wednesday}}})
    //i use different command
    @Override
    public void updateMedicalRecord(String id, LocalDateTime date, MedicalRecord newRecord) {
        Query query = getQueryById(id).
                addCriteria(Criteria.where(RecordFields.date.path).is(date));
        Update update = new Update().set(MedicalCard.field.records.nameDot$, newRecord);
        template.updateFirst(query, update, MedicalCard.class);
    }

    @Override
    public void deleteMedicalRecord(String id, LocalDateTime dateOfOldRecord) {
        Query query = getQueryById(id);
        Update update = new Update().pull(MedicalCard.field.records.name(),
                Query.query(Criteria.where(RecordFields.date.name()).is(dateOfOldRecord)));
        logger.info(update.toString());
        UpdateResult updateResult = template.updateFirst(query, update, MedicalCard.class);
        logger.info(updateResult.toString());
    }

    protected static Query getQueryById(String id) {
        return new Query().addCriteria(Criteria.where("_" + MedicalCard.field.id.name()).is(id));
    }
}
