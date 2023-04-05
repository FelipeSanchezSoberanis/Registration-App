package com.fiuady.registrationApp.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiuady.registrationApp.pojos.LoginRequest;
import com.fiuady.registrationApp.pojos.LoginResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter {

    AuthenticationProvider postgresAuthProvider;
    ObjectMapper objectMapper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {

        LoginRequest loginRequest;
        try {
            loginRequest = objectMapper.readValue(req.getInputStream(), LoginRequest.class);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        return postgresAuthProvider.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()));
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain,
            Authentication authResult)
            throws IOException, ServletException {
        AppUserDetails userDetails = (AppUserDetails) authResult.getPrincipal();

        String username = userDetails.getUsername();
        List<String> userAuthorities =
                userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
        Long userId = userDetails.getId();

        String accessToken =
                JWT.create()
                        .withSubject(username)
                        .withClaim("authorities", userAuthorities)
                        .withClaim("id", userId)
                        .withExpiresAt(Instant.now().plusMillis(JwtConstants.ACCESS_TOKEN_LIFETIME))
                        .sign(Algorithm.HMAC512(JwtConstants.SECRET));

        String refreshToken =
                JWT.create()
                        .withSubject(username)
                        .withExpiresAt(
                                Instant.now().plusMillis(JwtConstants.REFRESH_TOKEN_LIFETIME))
                        .sign(Algorithm.HMAC512(JwtConstants.SECRET));

        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.getWriter()
                .write(
                        objectMapper.writeValueAsString(
                                new LoginResponse(accessToken, refreshToken)));
    }
}
