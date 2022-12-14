package com.example.hospitalproject.userInfo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserInfo userId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appointment", referencedColumnName = "id")
    private Appointment appointment;
}
