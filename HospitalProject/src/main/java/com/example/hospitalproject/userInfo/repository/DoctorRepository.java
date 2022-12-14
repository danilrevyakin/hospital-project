package com.example.hospitalproject.userInfo.repository;

import com.example.hospitalproject.userInfo.model.Doctor;
import com.example.hospitalproject.userInfo.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByUserId(UserInfo userId);
}
