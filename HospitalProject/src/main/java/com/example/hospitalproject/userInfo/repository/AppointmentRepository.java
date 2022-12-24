package com.example.hospitalproject.userInfo.repository;

import com.example.hospitalproject.userInfo.model.Appointment;
import com.example.hospitalproject.userInfo.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDateAndTimeAndDoctor(Date date, Time start, Doctor doctor);
    List<Appointment> findByDoctor(Doctor doctor);
}
