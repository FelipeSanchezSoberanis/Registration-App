package com.fiuady.registrationApp.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiuady.registrationApp.pojos.ApiError;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtVerificationFilter extends OncePerRequestFilter {

    private AuthenticationProvider authProvider;
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = req.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(JwtConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication;
        try {
            authentication = getAuthentication(authHeader);
        } catch (TokenExpiredException ex) {
            returnTokenExpiredApiError(res, ex);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(req, res);
    }

    private void returnTokenExpiredApiError(HttpServletResponse res, TokenExpiredException ex) {
        ApiError apiError = new ApiError();
        apiError.setMessage(ex.getMessage());

        try {
            res.getWriter().write(objectMapper.writeValueAsString(apiError));
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String authHeader) {
        String token = authHeader.replace(JwtConstants.TOKEN_PREFIX, "");

        DecodedJWT jwt = JWT.require(Algorithm.HMAC512(JwtConstants.SECRET)).build().verify(token);

        String username = jwt.getSubject();
        Long userId = jwt.getClaim("id").as(Long.class);
        List<String> authoritiesAsString = jwt.getClaim("authorities").asList(String.class);
        List<SimpleGrantedAuthority> authorities =
                authoritiesAsString.stream()
                        .map(auth -> new SimpleGrantedAuthority(auth))
                        .collect(Collectors.toList());

        AppUserDetails userDetails = new AppUserDetails(authorities, "", username, userId);

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }
}
