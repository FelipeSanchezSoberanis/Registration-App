package com.fiuady.registrationApp.startup;

import com.fiuady.registrationApp.entities.Permission;
import com.fiuady.registrationApp.entities.Role;
import com.fiuady.registrationApp.entities.User;
import com.fiuady.registrationApp.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.crossstore.HashMapChangeSet;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class OnStartUp {

    @Autowired private PasswordEncoder argon2PasswordEncoder;
    @Autowired private UserRepository userRepo;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethng() {
        Permission permission = new Permission();
        permission.setName("create:something");

        Set<Permission> permSet = new HashSet<>();
        permSet.add(permission);

        Role role = new Role();
        role.setName("ROLE_some_role");
        role.setPermissions(permSet);

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        User user = new User();
        user.setUsername("felipe");
        user.setPassword(argon2PasswordEncoder.encode("password"));
        user.setRoles(roleSet);

        userRepo.save(user);
    }
}
