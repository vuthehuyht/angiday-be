package com.macrace.angidaybe.services;

import com.macrace.angidaybe.repositories.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String accessToken;
        if(authHeader == null || authHeader.startsWith("Bearer ")) {
            return;
        }

        accessToken = authHeader.substring(7);
        var token = tokenRepository.findByToken(accessToken).orElse(null);
        if (token != null) {
            token.setExpired(true);
            token.setRevoked(true);
            tokenRepository.save(token);
            SecurityContextHolder.clearContext();
        }
    }
}
