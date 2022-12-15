package com.example.hospitalproject.userInfo.repository;

import com.example.hospitalproject.userInfo.model.Doctor;
import com.example.hospitalproject.userInfo.model.DoctorType;
import com.example.hospitalproject.userInfo.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByUserId(UserInfo userId);
    List<Doctor> findByType(DoctorType type);
}
