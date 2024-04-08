package com.macrace.angidaybe.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.macrace.angidaybe.constant.ErrorCode.OTP_EXPIRED;

@Getter
@NoArgsConstructor
public class OtpExpiredException extends RuntimeException {
    private final String message = "OTP code is expired";
    private final String code = String.valueOf(OTP_EXPIRED);
}
