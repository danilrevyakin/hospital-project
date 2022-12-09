package com.example.hospitalproject;

import com.example.hospitalproject.medicalCard.MedicalCard;
import com.example.hospitalproject.medicalCard.MedicalCardRepository;
import com.example.hospitalproject.medicalCard.model.MedicalRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class HospitalProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalProjectApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(MedicalCardRepository repository) {
        MedicalRecord record = new MedicalRecord(
                "Info",
                "heart pain",
                "valeriana",
                LocalDate.now(),
                null
        );
        return args ->{
            MedicalCard medicalCard = new MedicalCard(
                    4L,
                    null,
                    List.of("Takes drugsііі"),
                    null,
                    LocalDate.now()
            );
            repository.insert(medicalCard);
        };
    }
}
