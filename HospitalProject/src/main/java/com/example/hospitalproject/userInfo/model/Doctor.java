package com.example.hospitalproject.userInfo.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserInfo userId;

    @Enumerated(EnumType.STRING)
    @Column(name="type")
    private DoctorType type;

    public Doctor(){}
    public Doctor(Long id, UserInfo userInfo, DoctorType doctorType) {
        this.id = id;
        this.userId = userInfo;
        this.type = doctorType;
    }
}
