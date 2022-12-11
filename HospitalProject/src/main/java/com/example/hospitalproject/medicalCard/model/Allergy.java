package com.example.hospitalproject.medicalCard.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Allergy {
    private String title;
    private String reaction;

    private static final String allergies = "allergies";

    public enum field {
        title(allergies + ".title"),
        reaction(allergies + ".reaction");
        public final String path;

        field(String path) {
            this.path = path;
        }
    }
}
