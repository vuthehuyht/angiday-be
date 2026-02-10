package com.macrace.angidaybe.controller;

import com.macrace.angidaybe.dto.request.OtpRequest;
import com.macrace.angidaybe.dto.response.ApiResponse;
import com.macrace.angidaybe.services.OtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/otp")
@RequiredArgsConstructor
@Slf4j
@Validated
public class OtpController {
    private final OtpService otpService;

    @PostMapping
    public ApiResponse<?> verifyOtp(@Valid @RequestBody OtpRequest request) {
        log.info("Handling verify OTP request");
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .build();
    }
}
