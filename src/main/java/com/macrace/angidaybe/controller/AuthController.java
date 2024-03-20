package com.macrace.angidaybe.controller;

import com.macrace.angidaybe.dto.request.AuthRequest;
import com.macrace.angidaybe.dto.request.RegisterRequest;
import com.macrace.angidaybe.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> login(@Valid @RequestBody AuthRequest request) {
        log.info("User logging in....");
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequest request) {
        log.info("Intercept registration new user");
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }
}
