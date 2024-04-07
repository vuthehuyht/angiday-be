package com.macrace.angidaybe.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OtpIncorrectException extends RuntimeException {
    private final String message = "OTP is incorrect";
    private final String code = "602";
}
