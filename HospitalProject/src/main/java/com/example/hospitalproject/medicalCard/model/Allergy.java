package com.example.hospitalproject.medicalCard.model;

import com.mongodb.lang.Nullable;

import java.util.Objects;

public record Allergy (String title,@Nullable String reaction){
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
}
