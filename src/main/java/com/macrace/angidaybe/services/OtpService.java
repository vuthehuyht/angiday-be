package com.macrace.angidaybe.services;

import com.macrace.angidaybe.dto.request.OtpRequest;

public interface OtpService {
    void verifyOtp(OtpRequest request);
}
