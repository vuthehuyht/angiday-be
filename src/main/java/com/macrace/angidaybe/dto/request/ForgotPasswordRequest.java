package com.macrace.angidaybe.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordRequest {
    @NotBlank(message = "PHONE_NUMBER_REQUIRED")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "PHONE_NUMBER_INVALID")
    @JsonProperty("phone_number")
    private String phoneNumber;
}
