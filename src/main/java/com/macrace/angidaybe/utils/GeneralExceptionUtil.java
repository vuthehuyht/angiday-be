package com.macrace.angidaybe.utils;

import com.macrace.angidaybe.exceptions.GeneralException;
import org.springframework.http.HttpStatus;

public class GeneralExceptionUtil {
    private GeneralExceptionUtil() {
    }

    public static GeneralException handleUnauthorized() {
        GeneralException customMessageException = new GeneralException();
        customMessageException.setMessage("");
        customMessageException.setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        return customMessageException;
    }

    public static GeneralException handleTokenUnavailable() {
        GeneralException customMessageException = new GeneralException();
        customMessageException.setMessage("Token is unavailable");
        customMessageException.setCode("606");
        return customMessageException;
    }
}
