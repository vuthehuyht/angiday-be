package com.macrace.angidaybe.services;

import com.macrace.angidaybe.dto.request.AuthRequest;
import com.macrace.angidaybe.dto.request.RegisterRequest;
import com.macrace.angidaybe.dto.response.AuthResponse;
import com.macrace.angidaybe.dto.response.RegisterResponse;

public interface AuthService {
    AuthResponse login(AuthRequest request);

    RegisterResponse register(RegisterRequest request);
}
