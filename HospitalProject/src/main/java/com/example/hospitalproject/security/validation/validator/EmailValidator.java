package com.example.hospitalproject.security.validation.validator;

import com.example.hospitalproject.security.validation.Matcher;
import com.example.hospitalproject.security.validation.constraint.EmailConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {

    @Override
    public void initialize(EmailConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return Matcher.checkEmail(s);
    }
}
