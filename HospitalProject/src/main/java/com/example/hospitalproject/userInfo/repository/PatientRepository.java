package com.example.hospitalproject.userInfo.repository;

import com.example.hospitalproject.userInfo.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long>{

}
