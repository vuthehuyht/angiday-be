package com.macrace.angidaybe.controller;

import com.macrace.angidaybe.dto.request.ForgotPasswordRequest;
import com.macrace.angidaybe.dto.response.ApiResponse;
import com.macrace.angidaybe.dto.response.ForgetPasswordResponse;
import com.macrace.angidaybe.services.UserService;
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
@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {
    private final UserService userService;

    @PostMapping(path = "/forget-password")
    public ApiResponse<ForgetPasswordResponse> forgetPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        log.info("Handling forget password request");
        return ApiResponse.<ForgetPasswordResponse>builder()
                .code(HttpStatus.OK.value())
                .result(userService.sentOtp(request))
                .build();
    }
}
