package com.macrace.angidaybe.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OtpExpiredException extends RuntimeException {
    private final String message = "OTP code is expired";
    private final String code = "601";
}
