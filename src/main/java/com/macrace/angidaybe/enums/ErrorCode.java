package com.macrace.angidaybe.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(1000, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_DUPLICATE(600, "Phone number exist", HttpStatus.BAD_REQUEST),
    USERNAME_OR_PASSWORD_WRONG(601, "Username or Password incorrect", HttpStatus.BAD_REQUEST),
    OTP_EXPIRED(602, "OTP code is expired", HttpStatus.BAD_REQUEST),
    OTP_WRONG(603, "OTP code is incorrect", HttpStatus.BAD_REQUEST),
    USERNAME_REQUIRED(604, "Username is required", HttpStatus.BAD_REQUEST),
    PASSWORD_REQUIRED(605, "Password is required", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(606, "Password at least 8 characters", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_REQUIRED(607, "Phone number is required", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_INVALID(608, "Phone number is invalid", HttpStatus.BAD_REQUEST),
    OTP_CODE_REQUIRED(609, "OTP Code is required", HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(Integer code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final Integer code;
    private final String message;
    private final HttpStatusCode statusCode;
}
