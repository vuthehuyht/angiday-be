package com.macrace.angidaybe.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.macrace.angidaybe.constant.ErrorCode.OTP_WRONG;

@Getter
@NoArgsConstructor
public class OtpIncorrectException extends RuntimeException {
    private final String message = "OTP is incorrect";
    private final String code = String.valueOf(OTP_WRONG);
}
