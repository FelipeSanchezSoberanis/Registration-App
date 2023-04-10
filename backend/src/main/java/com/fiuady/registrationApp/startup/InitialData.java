package com.fiuady.registrationApp.startup;

import com.fiuady.registrationApp.entities.User;
import com.fiuady.registrationApp.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitialData {

    @Autowired private PasswordEncoder argon2PasswordEncoder;
    @Autowired private UserRepository userRepo;

    @EventListener(ApplicationReadyEvent.class)
    public void createInitialData() {
        createInitialUserIfNoneExist();
    }

    private void createInitialUserIfNoneExist() {
        if (!userRepo.findAll().isEmpty()) return;

        User user = new User();
        user.setUsername("root");
        user.setPassword(argon2PasswordEncoder.encode("admin"));

        userRepo.save(user);
    }
}
