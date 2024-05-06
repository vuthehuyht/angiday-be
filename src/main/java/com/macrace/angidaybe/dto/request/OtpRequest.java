package com.macrace.angidaybe.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpRequest {
    @NotBlank(message = "PHONE_NUMBER_REQUIRED")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "OTP_CODE_REQUIRED")
    @JsonProperty("otp_code")
    private String otpCode;
}
