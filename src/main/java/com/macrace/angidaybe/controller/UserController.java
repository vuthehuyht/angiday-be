package com.macrace.angidaybe.controller;

import com.macrace.angidaybe.dto.request.ForgotPasswordRequest;
import com.macrace.angidaybe.dto.response.ForgetPasswordResponse;
import com.macrace.angidaybe.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {
    private final UserService userService;

    @PostMapping(path = "/forget-password")
    public ResponseEntity<ForgetPasswordResponse> forgetPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        log.info("Handling forget password request");
        return ResponseEntity.ok(userService.sentOtp(request));
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<Object> getUserById(@RequestParam(value = "user_id") Long userId) {
        log.info("Handling get user info request");
        return new ResponseEntity<>(userService.getUserInfo(userId), HttpStatus.OK);
    }
}
