package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecordMongoRepository extends MongoRepository<MedicalRecord, String> {

//    db.medicalCard.findOne({_id: "3", "records": {$elemMatch: {date: ISODate('2022-12-13T16:44:07.290Z')}}},
//    {records: 1, _id: 0})
    @Query("{'_id': :#{#_id}, \"records\": {$elemMatch {date: :#{#date}}},{records: 1, _id: 0}}")
    List<MedicalRecord> getMedicalRecordsByIdAndDate(@Param("_id") String id, @Param("date") LocalDateTime date);

//    db.medicalCard.findOneAndUpdate({ "_id": "1","records$": {"records.date": "2022-12-11T23:15:15.522"}},
//    {$set: updateObj})
    @Query("{'_id': :#{#_id}},{\"records$\": {\"records.date\": :#{#date}}}")
    @Update("$set: :#{#record}")
    void updateRecord(@Param("_id") String id,@Param("date") LocalDateTime date, @Param("record") MedicalRecord record);

}
