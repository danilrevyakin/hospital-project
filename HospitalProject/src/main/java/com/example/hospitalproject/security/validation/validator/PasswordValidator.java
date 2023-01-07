package com.example.hospitalproject.security.validation.validator;

import com.example.hospitalproject.security.validation.Matcher;
import com.example.hospitalproject.security.validation.constraint.PasswordConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return Matcher.checkPasswordLength(s);
    }
}
