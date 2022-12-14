package com.example.hospitalproject.userInfo.repository;

import com.example.hospitalproject.userInfo.model.Doctor;
import com.example.hospitalproject.userInfo.model.UserInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;


public class DoctorRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;


    @SuppressWarnings("unused")
    public List<Doctor> findByUserId(UserInfo id) {
        String hql = "SELECT d FROM Doctor d WHERE d.userId = :id";
        TypedQuery<Doctor> query = entityManager.createQuery(hql, Doctor.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
}
