package com.example.hospitalproject.userInfo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "patients")
@IdClass(PatientId.class)
public class Patient {
    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserInfo userId;

    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appointment", referencedColumnName = "id")
    private Appointment appointment;
}
