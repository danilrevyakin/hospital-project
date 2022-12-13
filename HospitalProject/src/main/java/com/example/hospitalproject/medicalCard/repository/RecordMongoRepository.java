package com.example.hospitalproject.medicalCard.repository;

import com.example.hospitalproject.medicalCard.model.MedicalCard;
import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RecordMongoRepository extends MongoRepository<MedicalCard, String> {
    //    db.medicalCard.find({_id: "1"},{"records$": {"records.date": "2022-12-11T23:15:15.522"}})

    @Query("{'_id': :#{#_id}},{\"records$\": {\"records.date\": :#{#date}}}")
    Optional<MedicalRecord> getMedicalRecord(@Param("_id") String id,@Param("date") LocalDateTime date);
}
