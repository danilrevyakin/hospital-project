package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

    @Override
    public List<MedicalRecord> getMedicalRecordsByIdAndDate(String id, LocalDateTime date) {
        return repository.getMedicalRecordsByIdAndDate(id, date);
    }

    @Override
    public void addMedicalRecord(String id, MedicalRecord record) {
        Query query = getQueryById(id);
        Update update = new Update().push(MedicalCard.field.records.name(), record);
        template.updateFirst(query, update, MedicalCard.class);
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
