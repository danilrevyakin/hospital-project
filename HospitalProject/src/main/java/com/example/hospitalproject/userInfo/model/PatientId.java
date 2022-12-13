package com.example.hospitalproject.userInfo.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class PatientId implements Serializable {
    private UserInfo userId;
    private Appointment appointment;
}
