package com.macrace.authservice.services.impl;

import com.macrace.authservice.constant.ErrorCode;
import com.macrace.authservice.custom.CUserDetail;
import com.macrace.authservice.dto.request.AuthRequest;
import com.macrace.authservice.dto.request.RegisterRequest;
import com.macrace.authservice.dto.response.AuthResponse;
import com.macrace.authservice.dto.response.RegisterResponse;
import com.macrace.authservice.exceptions.PhoneNumberExistException;
import com.macrace.authservice.models.Token;
import com.macrace.authservice.models.User;
import com.macrace.authservice.repositories.TokenRepository;
import com.macrace.authservice.repositories.UserRepository;
import com.macrace.authservice.services.AuthService;
import com.macrace.authservice.services.JwtService;
import com.macrace.authservice.types.TokenType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service


@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final CUserDetailService cUserDetailService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Override
    public AuthResponse login(AuthRequest request) {
        CUserDetail userDetail = (CUserDetail) cUserDetailService.loadUserByUsername(request.getUsername());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var accessToken = jwtService.generateToken(userDetail);
        var refreshToken = jwtService.refreshToken(userDetail);

        var token = Token.builder()
                .user(userRepository.findByUsername(request.getUsername()).get())
                .token(accessToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);

        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
        if (userOptional.isPresent()) {
            log.error("Phone number {} exist", request.getUsername());
            throw new PhoneNumberExistException("Phone number exist", String.valueOf(ErrorCode.PHONE_NUMBER_DUPLICATE));
        }

        User newUser = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        userRepository.save(newUser);
        log.info("Create new user with phone number {}", request.getUsername());
        return new RegisterResponse(
                "Đăng ký tài khoản mới thành công"
        );
    }
}