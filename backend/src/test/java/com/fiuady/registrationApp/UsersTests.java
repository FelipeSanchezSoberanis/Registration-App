package com.fiuady.registrationApp;

import static com.fiuady.registrationApp.utils.PermissionsPrefixes.USER_ROLE_PREFIX;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fiuady.registrationApp.entities.Role;
import com.fiuady.registrationApp.entities.User;
import com.fiuady.registrationApp.repositories.RoleRepository;
import com.fiuady.registrationApp.repositories.UserRepository;
import com.fiuady.registrationApp.services.UserService;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsersTests {

    @Autowired private UserService userService;
    @Autowired private UserRepository userRepo;
    @Autowired private RoleRepository roleRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    @Test
    @Order(1)
    void testCreateNewUser() {
        String username = "username";
        String password = "password";

        userService.createUser(username, password);

        Optional<User> createdUserOpt = userRepo.findByUsername(username);

        assertTrue(createdUserOpt.isPresent());

        User createdUser = createdUserOpt.get();

        assertEquals(username, createdUser.getUsername());
        assertTrue(passwordEncoder.matches(password, createdUser.getPassword()));

        Optional<Role> createdUserRoleOpt = roleRepo.findByName(USER_ROLE_PREFIX + username);

        assertTrue(createdUserRoleOpt.isPresent());

        Role createdUserRole = createdUserRoleOpt.get();

        assertTrue(createdUser.getRoles().contains(createdUserRole));
    }
}
