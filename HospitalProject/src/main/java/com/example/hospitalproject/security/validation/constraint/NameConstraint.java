package com.example.hospitalproject.security.validation.constraint;

import com.example.hospitalproject.security.validation.validator.NameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;


import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR })
@Retention(RetentionPolicy.RUNTIME)
public @interface NameConstraint {
    String message() default "Invalid input. Start with capital letter (min length = 2)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
