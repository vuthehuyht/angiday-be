package com.macrace.angidaybe.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.macrace.angidaybe.constant.ErrorCode.USER_NOT_FOUND;

@NoArgsConstructor
@Getter
public class UserNotFoundException extends RuntimeException {
    private final String message = "User not found";
    private final String code = String.valueOf(USER_NOT_FOUND);
}
