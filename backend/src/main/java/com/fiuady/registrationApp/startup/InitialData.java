package com.fiuady.registrationApp.startup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiuady.registrationApp.entities.Permission;
import com.fiuady.registrationApp.entities.Role;
import com.fiuady.registrationApp.entities.User;
import com.fiuady.registrationApp.repositories.PermissionRepository;
import com.fiuady.registrationApp.repositories.RoleRepository;
import com.fiuady.registrationApp.repositories.UserRepository;
import com.fiuady.registrationApp.utils.RandomUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class InitialData {

    @Autowired private PasswordEncoder argon2PasswordEncoder;
    @Autowired private UserRepository userRepo;
    @Autowired private RoleRepository roleRepo;
    @Autowired private PermissionRepository permRepo;
    @Autowired ObjectMapper objectMapper;

    @EventListener(ApplicationReadyEvent.class)
    public void createInitialData() {
        Set<Permission> permissions = createPermissions();
        Set<Role> roles = createRoles(permissions);
        createUsers(roles);
    }

    private Set<User> createUsers(Set<Role> roles) {
        Set<User> users = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            User user = new User();

            user.setUsername(String.format("user_%s", i + 1));
            user.setPassword(argon2PasswordEncoder.encode("password"));
            user.setRoles(RandomUtils.getRandomElementsFromSet(roles, 5));
        }

        userRepo.saveAllAndFlush(users);

        return users;
    }

    private Set<Role> createRoles(Set<Permission> permissions) {
        Set<Role> roles = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            Role role = new Role();
            role.setName(String.format("role_%s", i + 1));
            role.setPermissions(RandomUtils.getRandomElementsFromSet(permissions, 5));

            roles.add(role);
        }

        roleRepo.saveAllAndFlush(roles);

        return roles;
    }

    private Set<Permission> createPermissions() {
        Set<Permission> permissions = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            Permission permission = new Permission();
            permission.setName(String.format("permission_%s", i + 1));

            permissions.add(permission);
        }

        permRepo.saveAllAndFlush(permissions);

        return permissions;
    }
}
