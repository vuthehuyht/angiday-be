package com.macrace.angidaybe.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = AtLeast8CharacterValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AtLeast8Character {
    String message() default "PASSWORD_INVALID";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
