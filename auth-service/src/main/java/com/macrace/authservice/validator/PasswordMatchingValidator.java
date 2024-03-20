package com.macrace.authservice.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapperImpl;

@Slf4j
public class PasswordMatchingValidator implements ConstraintValidator<PasswordMatching, Object> {
    private String password;
    private String confirmPassword;
    private String message;

    @Override
    public void initialize(PasswordMatching matching) {
        this.password = matching.password();
        this.confirmPassword = matching.confirmPassword();
        message = matching.message();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;
        try {
            Object passwordValue = new BeanWrapperImpl(o).getPropertyValue(password);
            Object confirmPasswordValue = new BeanWrapperImpl(o).getPropertyValue(confirmPassword);

            valid = passwordValue == null && confirmPasswordValue == null || passwordValue != null && passwordValue.equals(confirmPasswordValue);
        } catch (Exception e) {
            //log.warn("Error {}", e.getMessage());
        }

        if (!valid) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }
}
