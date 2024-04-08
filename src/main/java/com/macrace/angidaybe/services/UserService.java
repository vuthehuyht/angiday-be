package com.macrace.angidaybe.services;

import com.macrace.angidaybe.dto.request.ForgotPasswordRequest;
import com.macrace.angidaybe.dto.response.ForgetPasswordResponse;
import com.macrace.angidaybe.dto.response.UserInfoResponse;

public interface UserService {
    ForgetPasswordResponse sentOtp(ForgotPasswordRequest request);

    UserInfoResponse getUserInfo(Long userId);
}
