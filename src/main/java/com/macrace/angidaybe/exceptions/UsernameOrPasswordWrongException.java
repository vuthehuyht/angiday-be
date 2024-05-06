package com.macrace.angidaybe.exceptions;

import com.macrace.angidaybe.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernameOrPasswordWrongException extends RuntimeException {
    private ErrorCode errorCode;

    public UsernameOrPasswordWrongException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
