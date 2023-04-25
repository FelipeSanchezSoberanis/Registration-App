package com.fiuady.registrationApp.startup;

import com.fiuady.registrationApp.repositories.UserRepository;
import com.fiuady.registrationApp.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class InitialData {

    @Autowired private UserRepository userRepo;
    @Autowired private UserService userService;

    @EventListener(ApplicationReadyEvent.class)
    public void createInitialData() {
        createInitialUserIfNoneExist();
    }

    private void createInitialUserIfNoneExist() {
        boolean usersExist = !userRepo.findAll().isEmpty();
        if (usersExist) return;

        userService.createUser("root", "admin");
    }
}
