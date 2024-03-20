package com.macrace.angidaybe.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordRequest {
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number wrong")
    @JsonProperty("phone_number")
    private String phoneNumber;
}
