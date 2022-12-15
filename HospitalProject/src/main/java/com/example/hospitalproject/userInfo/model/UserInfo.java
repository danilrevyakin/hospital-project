package com.example.hospitalproject.userInfo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;

@Entity
@Data
@Table(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private Date birthday;
    private String phone;
}
