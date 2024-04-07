package com.macrace.angidaybe.services;

import com.macrace.angidaybe.dto.request.OtpRequest;
import com.macrace.angidaybe.dto.response.OtpResponse;

public interface OtpService {
    OtpResponse verifyOtp(OtpRequest request);
}
