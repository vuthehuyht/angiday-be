package com.macrace.authservice.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;

@NoArgsConstructor
public class PhoneNumberExistException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("message")
    private String message;

    @Getter
    @JsonProperty("code")
    private String code;

    public PhoneNumberExistException(String message, String code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
