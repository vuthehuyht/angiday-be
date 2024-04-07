package com.macrace.angidaybe.controller;

import com.macrace.angidaybe.dto.request.OtpRequest;
import com.macrace.angidaybe.services.OtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody OtpRequest request) {
        log.info("Handling verify OTP request");
        return ResponseEntity.ok().body(otpService.verifyOtp(request));
    }
}
