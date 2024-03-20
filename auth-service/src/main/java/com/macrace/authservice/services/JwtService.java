package com.macrace.authservice.services;

import com.macrace.authservice.custom.CUserDetail;
import io.jsonwebtoken.Claims;

import java.security.Key;

public interface JwtService {
    Claims extractClaims(String token);

    Key getKey();

    String generateToken(CUserDetail customUserDetail);

    String refreshToken(CUserDetail customUserDetail);

    boolean isValidToken(String token);
}
