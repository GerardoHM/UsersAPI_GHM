package com.chakray.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AndresValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AndresFormat {
    String message() default "Invalid Andres phone format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
