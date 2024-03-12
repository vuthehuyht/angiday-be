package com.macrace.angidaybe.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.macrace.angidaybe.config.JwtConfig;
import com.macrace.angidaybe.services.JwtService;
import com.macrace.angidaybe.utils.GeneralExceptionUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    private final JwtConfig jwtConfig;
    private final List<String> ALLOW_PATH = new ArrayList<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Start filter {}", request.getRequestURI());

        if (ALLOW_PATH.contains(request.getServletPath())) {
            filterChain.doFilter(request, response);
            return;
        }

        var accessToken = request.getHeader(jwtConfig.getHeader());
        if (accessToken != null && !accessToken.isBlank() && accessToken.startsWith(jwtConfig.getPrefix() + " ")) {
            accessToken = accessToken.substring((jwtConfig.getPrefix()).length());

            try {
                if (jwtService.isValidToken(accessToken)) {
                    Claims claims = jwtService.extractClaims(accessToken);
                    var username = claims.getSubject();

                    List<String> authorities = claims.get("authorities", List.class);
                    if (username != null) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                        );
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            } catch (Exception e) {
                log.error("Error message: {}", e.getLocalizedMessage());
                var msgError = GeneralExceptionUtil.handleUnauthorized();
                var msgJson = objectMapper.writeValueAsString(msgError);

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write(msgJson);
            }
        }

        log.info("End filter {}", request.getRequestURI());
        filterChain.doFilter(request, response);
    }
}
