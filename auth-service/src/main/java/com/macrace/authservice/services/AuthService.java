package com.macrace.authservice.services;

import com.macrace.authservice.dto.request.AuthRequest;
import com.macrace.authservice.dto.request.RegisterRequest;
import com.macrace.authservice.dto.response.AuthResponse;
import com.macrace.authservice.dto.response.RegisterResponse;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    RegisterResponse register(RegisterRequest request);
}
