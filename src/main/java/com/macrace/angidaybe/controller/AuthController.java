package com.macrace.angidaybe.controller;

import com.macrace.angidaybe.dto.request.AuthRequest;
import com.macrace.angidaybe.dto.request.RegisterRequest;
import com.macrace.angidaybe.dto.response.ApiResponse;
import com.macrace.angidaybe.dto.response.AuthResponse;
import com.macrace.angidaybe.dto.response.RegisterResponse;
import com.macrace.angidaybe.services.AuthService;
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
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        log.info("User logging in....");
        return ApiResponse.<AuthResponse>builder()
                .code(HttpStatus.OK.value())
                .result(authService.login(request))
                .build();
    }

    @PostMapping(path = "/register")
    public ApiResponse<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        log.info("Intercept registration new user");
        return ApiResponse.<RegisterResponse>builder()
                .code(HttpStatus.OK.value())
                .result(authService.register(request))
                .build();
    }
}
