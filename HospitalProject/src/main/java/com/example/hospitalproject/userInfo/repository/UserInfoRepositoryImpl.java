package com.example.hospitalproject.userInfo.repository;

import com.example.hospitalproject.userInfo.model.UserInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserInfoRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;


    @SuppressWarnings("unused")
    public List<UserInfo> findByEmail(String email) {
        String hql = "SELECT u FROM UserInfo u WHERE u.email = :email";
        TypedQuery<UserInfo> query = entityManager.createQuery(hql, UserInfo.class);
        query.setParameter("email", email);
        return query.getResultList();
    }
}
