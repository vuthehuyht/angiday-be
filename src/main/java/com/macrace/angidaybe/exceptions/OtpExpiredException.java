package com.macrace.angidaybe.exceptions;

import com.macrace.angidaybe.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OtpExpiredException extends RuntimeException {
    private ErrorCode errorCode;

    public OtpExpiredException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
