package com.macrace.angidaybe.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AtLeast8CharacterValidator implements ConstraintValidator<AtLeast8Character, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.length() >= 8;
    }
}
