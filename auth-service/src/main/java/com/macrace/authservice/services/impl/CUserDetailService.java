package com.macrace.authservice.services.impl;

import com.macrace.authservice.custom.CUserDetail;
import com.macrace.authservice.exceptions.GeneralException;
import com.macrace.authservice.models.User;
import com.macrace.authservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            log.warn("Username {} not found", username);
            throw new GeneralException("Username not found", String.valueOf(HttpStatus.NOT_FOUND.value()));
        }

        return new CUserDetail(
                userOptional.get().getUsername(),
                userOptional.get().getPassword()
        );
    }
}