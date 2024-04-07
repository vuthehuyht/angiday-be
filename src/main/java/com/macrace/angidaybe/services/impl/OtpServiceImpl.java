package com.macrace.angidaybe.services.impl;

import com.macrace.angidaybe.dto.request.OtpRequest;
import com.macrace.angidaybe.exceptions.OtpExpiredException;
import com.macrace.angidaybe.exceptions.OtpIncorrectException;
import com.macrace.angidaybe.services.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void verifyOtp(OtpRequest request) {
        String otpCode = redisTemplate.opsForValue().get(request.getPhoneNumber());
        redisTemplate.delete(request.getPhoneNumber());
        if (otpCode == null) {
            throw new OtpExpiredException();
        }
        if (!otpCode.equals(request.getOtpCode())) {
            throw new OtpIncorrectException();
        }
    }
}
