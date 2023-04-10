package com.fiuady.registrationApp.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fiuady.registrationApp.config.AppUserDetails;
import com.fiuady.registrationApp.config.JwtConstants;
import com.fiuady.registrationApp.config.PostgresUserDetailsService;
import com.fiuady.registrationApp.pojos.LoginResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MainController {

    @Autowired private PostgresUserDetailsService postgresUserDetailsService;

    @PostMapping("/refreshToken")
    public ResponseEntity<LoginResponse> getNewAccessToken(
            @RequestBody LoginResponse loginResponse) {
        String refreshToken = loginResponse.getRefreshToken();

        DecodedJWT jwt =
                JWT.require(Algorithm.HMAC512(JwtConstants.SECRET)).build().verify(refreshToken);

        String username = jwt.getSubject();
        Long userId = jwt.getClaim("id").asLong();

        AppUserDetails userDetails = postgresUserDetailsService.loadUserByUsername(username);

        List<String> userAuthorities =
                userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());

        String accessToken =
                JWT.create()
                        .withSubject(username)
                        .withClaim("authorities", userAuthorities)
                        .withClaim("id", userId)
                        .withExpiresAt(Instant.now().plusMillis(JwtConstants.ACCESS_TOKEN_LIFETIME))
                        .sign(Algorithm.HMAC512(JwtConstants.SECRET));

        LoginResponse newAccessToken = new LoginResponse();
        newAccessToken.setAccessToken(accessToken);

        return new ResponseEntity<>(newAccessToken, HttpStatus.OK);
    }
}
