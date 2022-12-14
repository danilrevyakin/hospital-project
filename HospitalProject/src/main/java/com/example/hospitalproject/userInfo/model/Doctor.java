package com.example.hospitalproject.userInfo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserInfo userId;

    @Enumerated(EnumType.STRING)
    @Column(name="type")
    private DoctorType type;
}
