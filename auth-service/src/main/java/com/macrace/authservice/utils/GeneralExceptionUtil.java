package com.macrace.authservice.utils;

import com.macrace.authservice.exceptions.GeneralException;
import org.springframework.http.HttpStatus;

public class GeneralExceptionUtil {
    private GeneralExceptionUtil() {}

    public static GeneralException handleUnauthorized() {
        GeneralException customMessageException =  new GeneralException();
        customMessageException.setMessage("");
        customMessageException.setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        return customMessageException;
    }
}
