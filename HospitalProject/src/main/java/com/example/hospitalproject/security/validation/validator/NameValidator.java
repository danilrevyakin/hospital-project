package com.example.hospitalproject.security.validation.validator;

import com.example.hospitalproject.security.validation.Matcher;
import com.example.hospitalproject.security.validation.constraint.NameConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class NameValidator implements ConstraintValidator<NameConstraint, String> {

    @Override
    public void initialize(NameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return Matcher.checkName(s);
    }
}
