package com.macrace.angidaybe.exceptions;

import com.macrace.angidaybe.enums.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OtpIncorrectException extends RuntimeException {
    private ErrorCode errorCode;

    public OtpIncorrectException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
