package com.macrace.angidaybe.dto.request;

import com.macrace.angidaybe.validator.AtLeast8Character;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthRequest {
    @NotBlank(message = "USERNAME_REQUIRED")
    private String username;

    @NotBlank(message = "PASSWORD_REQUIRED")
    @AtLeast8Character
    private String password;
}
