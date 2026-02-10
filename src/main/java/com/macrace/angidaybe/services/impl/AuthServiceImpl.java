package com.macrace.angidaybe.services.impl;

import com.macrace.angidaybe.custom.CUserDetail;
import com.macrace.angidaybe.dto.request.AuthRequest;
import com.macrace.angidaybe.dto.request.RegisterRequest;
import com.macrace.angidaybe.dto.response.AuthResponse;
import com.macrace.angidaybe.dto.response.RegisterResponse;
import com.macrace.angidaybe.enums.ErrorCode;
import com.macrace.angidaybe.exceptions.PhoneNumberExistException;
import com.macrace.angidaybe.exceptions.UsernameOrPasswordWrongException;
import com.macrace.angidaybe.models.Role;
import com.macrace.angidaybe.models.Token;
import com.macrace.angidaybe.models.User;
import com.macrace.angidaybe.repositories.RoleRepository;
import com.macrace.angidaybe.repositories.TokenRepository;
import com.macrace.angidaybe.repositories.UserRepository;
import com.macrace.angidaybe.services.AuthService;
import com.macrace.angidaybe.services.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
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
    private final RoleRepository roleRepository;

    @Override
    public AuthResponse login(AuthRequest request) {
        CUserDetail userDetail = (CUserDetail) cUserDetailService.loadUserByUsername(request.getUsername());
        if (!passwordEncoder.matches(request.getPassword(), userDetail.getPassword())) {
            throw new UsernameOrPasswordWrongException(ErrorCode.USERNAME_OR_PASSWORD_WRONG);
        }

        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
        List<Token> tokens = tokenRepository.findAllTokenByUserId(userOptional.get().getId());
        tokens.forEach(token -> {
            token.setExpired(true);
            tokenRepository.save(token);
        });

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var accessToken = jwtService.generateToken(userDetail);
        var refreshToken = jwtService.refreshToken(userDetail);

        var token = Token.builder()
                .user(userOptional.get())
                .token(accessToken)
                .expired(false)
                .build();
        tokenRepository.save(token);

        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
        if (userOptional.isPresent()) {
            log.error("Phone number {} exist", request.getUsername());
            throw new PhoneNumberExistException(ErrorCode.PHONE_NUMBER_DUPLICATE);
        }

        Role role = roleRepository.findByName("ROLE_USER").get();
        User newUser = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .roles(Collections.singleton(role))
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
