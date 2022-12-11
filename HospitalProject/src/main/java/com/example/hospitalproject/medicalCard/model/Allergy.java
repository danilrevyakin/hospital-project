package com.example.hospitalproject.medicalCard.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Allergy allergy = (Allergy) o;
        return Objects.equals(title, allergy.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return "Allergy{" +
                "title='" + title + '\'' +
                '}';
    }
}
