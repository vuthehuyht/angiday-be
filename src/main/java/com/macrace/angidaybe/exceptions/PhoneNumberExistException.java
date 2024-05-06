package com.macrace.angidaybe.exceptions;

import com.macrace.angidaybe.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PhoneNumberExistException extends RuntimeException {
    private ErrorCode errorCode;

    public PhoneNumberExistException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
