package com.macrace.angidaybe.services;

import com.macrace.angidaybe.dto.request.ForgotPasswordRequest;
import com.macrace.angidaybe.dto.response.ForgetPasswordResponse;

public interface UserService {
    ForgetPasswordResponse sentOtp(ForgotPasswordRequest request);
}
