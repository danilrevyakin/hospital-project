package com.example.hospitalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class HospitalProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalProjectApplication.class, args);
    }

}
