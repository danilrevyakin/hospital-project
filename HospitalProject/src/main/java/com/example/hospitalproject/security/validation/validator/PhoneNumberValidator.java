package com.example.hospitalproject.security.validation.validator;


import com.example.hospitalproject.security.validation.Matcher;
import com.example.hospitalproject.security.validation.constraint.PhoneNumberConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberConstraint, String> {

    @Override
    public void initialize(PhoneNumberConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return Matcher.checkPhoneNumber(s);
    }
}
