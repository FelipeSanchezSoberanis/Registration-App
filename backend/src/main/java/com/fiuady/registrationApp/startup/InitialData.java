package com.fiuady.registrationApp.startup;

import com.fiuady.registrationApp.entities.Role;
import com.fiuady.registrationApp.entities.User;
import com.fiuady.registrationApp.repositories.RoleRepository;
import com.fiuady.registrationApp.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class InitialData {

    @Autowired private PasswordEncoder argon2PasswordEncoder;
    @Autowired private UserRepository userRepo;
    @Autowired private RoleRepository roleRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void createInitialData() {
        createInitialUserIfNoneExist();
    }

    private void createInitialUserIfNoneExist() {
        boolean usersExist = !userRepo.findAll().isEmpty();
        if (usersExist) return;

        Role role = new Role();
        role.setName("ROLE_user_root");
        roleRepository.saveAndFlush(role);

        User user = new User();
        user.setUsername("root");
        user.setPassword(argon2PasswordEncoder.encode("admin"));
        user.setRoles(Set.of(role));

        userRepo.save(user);
    }
}
