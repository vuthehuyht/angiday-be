package com.macrace.authservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.macrace.authservice.validator.PasswordMatching;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PasswordMatching(
        password = "password",
        confirmPassword = "confirmPassword",
        message = "Password and Confirm Password must be matched!"
)
public class RegisterRequest {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password is at least 8 characters")
    private String password;

    @NotBlank(message = "Confirm password is required")
    @JsonProperty("confirm_password")
    private String confirmPassword;

    @JsonProperty("full_name")
    private String fullName;
}
