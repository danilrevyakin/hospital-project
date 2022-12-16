package com.example.hospitalproject.userInfo.repository;

import com.example.hospitalproject.userInfo.model.Appointment;
import com.example.hospitalproject.userInfo.model.Doctor;
import com.example.hospitalproject.userInfo.model.DoctorType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class AppointmentRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unused")
    public List<Appointment> findByDateAndTimeAndDoctor(Date date, Time start, Doctor doctor) {
        String hql = "SELECT a FROM Appointment a WHERE a.day = :date AND a.start = :start AND a.doctor = :doctor";
        TypedQuery<Appointment> query = entityManager.createQuery(hql, Appointment.class);
        query.setParameter("date", date);
        query.setParameter("start", start);
        query.setParameter("doctor", doctor);
        return query.getResultList();
    }

    @SuppressWarnings("unused")
    public List<Appointment> findByDoctor(Doctor doctor) {
        String hql = "SELECT a FROM Appointment a WHERE  a.doctor = :doctor";
        TypedQuery<Appointment> query = entityManager.createQuery(hql, Appointment.class);
        query.setParameter("doctor", doctor);
        return query.getResultList();
    }
}
