package com.fiuady.registrationApp.startup;

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
import java.util.stream.Collectors;

@Configuration
public class InitialData {

    @Autowired private PasswordEncoder argon2PasswordEncoder;
    @Autowired private UserRepository userRepo;
    @Autowired private RoleRepository roleRepo;
    @Autowired private PermissionRepository permRepo;

    @EventListener(ApplicationReadyEvent.class)
    public void createInitialData() {
        createPermissions();
        createRoles();
        createUsers();
    }

    private void createUsers() {
        Set<Role> roles = roleRepo.findAll().stream().collect(Collectors.toSet());
        Set<User> users = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUsername(String.format("user_%s", i + 1));
            user.setPassword(argon2PasswordEncoder.encode("password"));
            user.setRoles(RandomUtils.getRandomElementsFromSet(roles, 5));

            users.add(user);
        }

        userRepo.saveAll(users);
    }

    private void createRoles() {
        Set<Permission> permissions = permRepo.findAll().stream().collect(Collectors.toSet());
        Set<Role> roles = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            Role role = new Role();
            role.setName(String.format("role_%s", i + 1));
            role.setPermissions(RandomUtils.getRandomElementsFromSet(permissions, 5));

            roles.add(role);
        }

        roleRepo.saveAll(roles);
    }

    private void createPermissions() {
        Set<Permission> permissions = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            Permission permission = new Permission();
            permission.setName(String.format("permission_%s", i + 1));

            permissions.add(permission);
        }

        permRepo.saveAll(permissions);
    }
}
