package com.macrace.authservice.exceptions;


import com.macrace.authservice.constant.ErrorCode;
import com.macrace.authservice.dto.response.ErrorResponseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ErrorResponseTemplate> handleGeneralException(GeneralException e) {
        return ResponseEntity.ok(
                new ErrorResponseTemplate(e.getMessage(), e.getCode(), null)
        );
    }

    @ExceptionHandler(PhoneNumberExistException.class)
    public ResponseEntity<Object> handlePhoneNumberExistException(PhoneNumberExistException e) {
        log.error("PhoneNumberExistException {}", e.getMessage());
        return new ResponseEntity<>(new ErrorResponseTemplate(
                "Phone number exists",
                String.valueOf(ErrorCode.PHONE_NUMBER_DUPLICATE),
                null
        ), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", new Date());
        responseBody.put("status", status.value());

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        responseBody.put("errors", errors);
        log.info(responseBody.toString());
        return new ResponseEntity<>(responseBody, headers, status);
    }
}
