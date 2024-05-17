package com.company.annotation.processor;

import com.company.annotation.validation.FullName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FullNameAnnotationProcessor implements ConstraintValidator<FullName, String> {
    private String regex;
    @Override
    public void initialize(FullName fullName) {
        this.regex = fullName.regex();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches(regex);
    }
}
