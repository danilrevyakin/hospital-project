package com.example.hospitalproject.medicalCard.repository.implementation;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import com.example.hospitalproject.medicalCard.repository.ArrayRepository;
import com.example.hospitalproject.medicalCard.repository.MedicalRecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
@AllArgsConstructor
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {

    private final ArrayRepository arrayRepository;

    private static final String records = "records";

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

    //    db.medicalCard.findOne({ "_id" : "3", "records" : { "$elemMatch" : { "info" : "some info"}}},
    //    { "records.$" : 1, "_id" : 0})
    @Override
    public List<MedicalRecord> getMedicalRecordsByIdAndDate(String id, LocalDateTime date) {
        Query query = getQueryById(id)
                .addCriteria(Criteria.where(MedicalCard.field.records.name())
                        .elemMatch(Criteria.where(RecordFields.date.name()).is(date)));
        return arrayRepository.getArrayFromCardByQuery(query,
                MedicalCard.field.records.nameDot$,
                List.of(), MedicalCard::getRecords);
    }

    //db.medicalCard.find({_id:"3"},{records: 1, _id: 0})
    @Override
    public List<MedicalRecord> getMedicalRecordsById(String id) {
        return arrayRepository.getArrayFromCardById(id, MedicalCard.field.records, List.of(), MedicalCard::getRecords);
    }

    @Override
    public void addMedicalRecord(String id, MedicalRecord record) {
        String array = MedicalCard.field.records.name();
        arrayRepository.pushArrayElement(id, array, record);
    }

    @Override
    public void updateMedicalRecord(String id, LocalDateTime date, MedicalRecord newRecord) {
        arrayRepository.updateArrayElement(id,
                RecordFields.date.path, date,
                MedicalCard.field.records.nameDot$, newRecord);
    }

    @Override
    public void deleteMedicalRecord(String id, LocalDateTime dateOfOldRecord) {
        String array = MedicalCard.field.records.name();
        String elementField = RecordFields.date.name();
        arrayRepository.deleteArrayElement(id, array, elementField, dateOfOldRecord);
    }

    protected static Query getQueryById(String id) {
        return new Query().addCriteria(Criteria.where("_" + MedicalCard.field.id.name()).is(id));
    }
}
