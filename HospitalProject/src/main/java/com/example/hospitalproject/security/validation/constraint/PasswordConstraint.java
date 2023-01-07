package com.example.hospitalproject.security.validation.constraint;

import com.example.hospitalproject.security.validation.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;


import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
    String message() default "Invalid password (min length = 8, max length = 64)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
