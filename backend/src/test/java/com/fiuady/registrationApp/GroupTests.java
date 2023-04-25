package com.fiuady.registrationApp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiuady.registrationApp.config.AppUserDetails;
import com.fiuady.registrationApp.entities.User;
import com.fiuady.registrationApp.repositories.GroupRepository;
import com.fiuady.registrationApp.repositories.UserRepository;
import com.fiuady.registrationApp.services.GroupService;
import com.fiuady.registrationApp.services.UserService;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GroupTests {

    @Autowired private GroupService groupService;
    @Autowired private GroupRepository groupRepo;
    @Autowired private UserService userService;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private UserRepository userRepo;

    void setupTestUser() {
        userService.createUser("username", "password");
        User user = userRepo.findByUsername("username").get();
        user.getRoles().forEach(r -> r.setPermissions(new HashSet<>()));
        AppUserDetails details = new AppUserDetails().buildFromUser(user);
        Authentication auth = Mockito.mock(Authentication.class);
        Mockito.when(auth.getPrincipal()).thenReturn(details);
        SecurityContext context = Mockito.mock(SecurityContext.class);
        Mockito.when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);
    }

    @Test
    @Order(1)
    void testCreateNewGroupWithNoRegistrationEvents() {
        setupTestUser();

        String groupName = "New group";

        groupService.createNewGroup(groupName);

        assertTrue(groupRepo.existsByName(groupName));
    }
}
