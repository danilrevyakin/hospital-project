package com.example.hospitalproject.userInfo.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name="appointment")
@Data
public class Appointment {
    @Id
    @GeneratedValue
    private Long id;

    private Date day;
    private Time start;
    private Time finish;
    private String description;
    private Boolean offline;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserInfo userInfo;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne
    @JoinColumn(name = "doctor", referencedColumnName = "user_id")
    private Doctor doctor;
}
