package com.macrace.angidaybe.services.impl;

import com.macrace.angidaybe.config.JwtConfig;
import com.macrace.angidaybe.custom.CUserDetail;
import com.macrace.angidaybe.exceptions.GeneralException;
import com.macrace.angidaybe.services.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtServiceImpl extends JwtConfig implements JwtService {
    private final CUserDetailService cUserDetailService;

    @Override
    public Claims extractClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    @Override
    public Key getKey() {
        byte[] keys = Decoders.BASE64.decode(getSecret());
        return Keys.hmacShaKeyFor(keys);
    }

    @Override
    public String generateToken(CUserDetail customUserDetail) {
        Instant currentDateTime = Instant.now();
        return Jwts.builder()
                .setSubject(customUserDetail.getUsername())
                .claim("isEnable", customUserDetail.isEnabled())
                .setIssuedAt(Date.from(currentDateTime))
                .setExpiration(Date.from(currentDateTime.plusSeconds(getExpiration())))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String refreshToken(CUserDetail customUserDetail) {
        Instant currentDateTime = Instant.now();
        return Jwts.builder()
                .setSubject(customUserDetail.getUsername())
                .claim("isEnable", customUserDetail.isEnabled())
                .setIssuedAt(Date.from(currentDateTime))
                .setExpiration(Date.from(currentDateTime.plusSeconds(getExpiration())))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isValidToken(String token) {
        final String username = extractUsernameByToken(token);
        UserDetails userDetails = cUserDetailService.loadUserByUsername(username);

        return userDetails != null;
    }

    private String extractUsernameByToken(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractClaimsByToken(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractClaimsByToken(String token) {
        var unauthorized = String.valueOf(HttpStatus.UNAUTHORIZED.value());
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJwt(token)
                    .getBody();
        } catch (UnsupportedJwtException e) {
            log.error("Unsupport JWT token {}", e.getLocalizedMessage());
            throw new GeneralException("Token not support", unauthorized);
        } catch (SignatureException | MalformedJwtException e) {
            log.error("Token is invalid format {}", e.getLocalizedMessage());
            throw new GeneralException("Token is invalid format", unauthorized);
        } catch (ExpiredJwtException e) {
            log.error("Token is expired {}", e.getLocalizedMessage());
            throw new GeneralException("Token is expired", unauthorized);
        } catch (Exception e) {
            log.error("{}", e.getLocalizedMessage());
            throw new GeneralException("Unknown error", unauthorized);
        }
    }
}
