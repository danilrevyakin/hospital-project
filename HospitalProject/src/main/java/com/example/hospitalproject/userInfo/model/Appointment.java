package com.example.hospitalproject.userInfo.model;

import jakarta.persistence.*;
import lombok.Data;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor", referencedColumnName = "user_id")
    private Doctor doctor;
}
