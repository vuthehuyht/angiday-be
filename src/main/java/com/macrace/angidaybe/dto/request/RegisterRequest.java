package com.macrace.angidaybe.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.macrace.angidaybe.validator.AtLeast8Character;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "USERNAME_REQUIRED")
    private String username;

    @NotBlank(message = "PASSWORD_REQUIRED")
    @AtLeast8Character
    private String password;

    @JsonProperty("full_name")
    private String fullName;
}
