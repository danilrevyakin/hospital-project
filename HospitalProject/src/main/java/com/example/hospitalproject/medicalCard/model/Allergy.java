package com.example.hospitalproject.medicalCard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Allergy {
    private String title;
    private String reaction;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Allergy allergy = (Allergy) o;
        if(allergy.title == null){
            return null == title;
        }
        if(title == null){
            return false;
        }
        return allergy.title.equalsIgnoreCase(title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title.toLowerCase());
    }

    @Override
    public String toString() {
        return "Allergy{" +
                "title='" + title + '\'' +
                '}';
    }
}
