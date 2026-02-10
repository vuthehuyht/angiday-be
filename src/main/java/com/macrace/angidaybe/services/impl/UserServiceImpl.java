package com.macrace.angidaybe.services.impl;

import com.macrace.angidaybe.config.SoapConfig;
import com.macrace.angidaybe.dto.request.ForgotPasswordRequest;
import com.macrace.angidaybe.dto.response.ForgetPasswordResponse;
import com.macrace.angidaybe.services.UserService;
import com.macrace.angidaybe.tasks.SendSmsTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl extends SoapConfig implements UserService {
    private final TaskExecutor executor;
    private final RedisTemplate<String, String> redisTemplate;
    @Value("${redis.time-to-live-otp}")
    private int TIME_TO_LIVE_OTP;

    @Override
    public ForgetPasswordResponse sentOtp(ForgotPasswordRequest request) {
        String otpCode = generateOTP();
        String message = "Ma OTP cua ban la "
                + otpCode +
                " hieu luc trong 1 phut. Khong duoc chia se ma nay voi nguoi khac";

        redisTemplate.opsForValue().set(request.getPhoneNumber(), otpCode, TIME_TO_LIVE_OTP, TimeUnit.SECONDS);
        executor.execute(new SendSmsTask(
                getEndpoint(),
                getUser(),
                getPassword(),
                getCpCode(),
                getBrandName(),
                request.getPhoneNumber(),
                message
        ));

        log.info("OTP was sent {}", request.getPhoneNumber());
        return new ForgetPasswordResponse("Your OTP was sent. Please wait");
    }

    private String generateOTP() {
        int randomPin = (int) ((Math.random() * 9000) + 1000);
        return String.valueOf(randomPin);
    }
}
