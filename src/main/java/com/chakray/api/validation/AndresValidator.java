package com.chakray.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AndresValidator implements ConstraintValidator<AndresFormat, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches("^(\\+\\d{1,3})?\\d{10}$");
    }
}
