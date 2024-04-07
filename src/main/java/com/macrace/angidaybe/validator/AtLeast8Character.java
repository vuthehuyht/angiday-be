package com.macrace.angidaybe.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = AtLeast8CharacterValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AtLeast8Character {
    String message() default "Password at least 8 characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
