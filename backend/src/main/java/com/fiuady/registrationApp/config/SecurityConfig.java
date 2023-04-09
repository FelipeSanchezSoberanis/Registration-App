package com.fiuady.registrationApp.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired private PostgresUserDetailsService postgresUserDetailsService;
    @Autowired ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests()
                .requestMatchers("/login")
                .permitAll()
                .anyRequest()
                .authenticated();

        http.addFilter(new JwtAuthFilter(postgresAuthProvider(), objectMapper));
        http.addFilterAfter(
                new JwtVerificationFilter(postgresAuthProvider(), objectMapper),
                JwtAuthFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder argon2PasswordEncoder() {
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public AuthenticationProvider postgresAuthProvider() {
        DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();

        daoAuthProvider.setUserDetailsService(postgresUserDetailsService);
        daoAuthProvider.setPasswordEncoder(argon2PasswordEncoder());

        return daoAuthProvider;
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("*").allowedOriginPatterns("*");
            }
        };
    }
}
