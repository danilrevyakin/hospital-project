package com.example.hospitalproject.userInfo.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name="appointment")
@Data
public class Appointment {
    @Id
    private Long id;

    private Date day;
    private Timestamp start;
    private Timestamp finish;
    private String description;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne
    @JoinColumn(name = "doctor", referencedColumnName = "user_id")
    private Doctor doctor;
}
