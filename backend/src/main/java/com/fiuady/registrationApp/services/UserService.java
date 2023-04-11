package com.fiuady.registrationApp.services;

import static com.fiuady.registrationApp.utils.PermissionsPrefixes.USER_ROLE_PREFIX;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiuady.registrationApp.config.AppUserDetails;
import com.fiuady.registrationApp.entities.Permission;
import com.fiuady.registrationApp.entities.Role;
import com.fiuady.registrationApp.entities.User;
import com.fiuady.registrationApp.exceptions.UsernameTakenException;
import com.fiuady.registrationApp.repositories.PermissionRepository;
import com.fiuady.registrationApp.repositories.RoleRepository;
import com.fiuady.registrationApp.repositories.UserRepository;
import com.fiuady.registrationApp.utils.PermissionsPrefixes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired private UserRepository userRepo;
    @Autowired private PermissionRepository permissionRepo;
    @Autowired private RoleRepository roleRepo;
    @Autowired private ObjectMapper objectMapper;

    public User getLoggedInUser() {
        AppUserDetails userDetails =
                (AppUserDetails)
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = new User();
        user.setUsername(userDetails.getUsername());
        user.setId(userDetails.getId());

        return user;
    }

    public boolean loggedInUserHasPermission(String permissionName) {
        AppUserDetails userDetails =
                (AppUserDetails)
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userDetails.getAuthorities().contains(new SimpleGrantedAuthority(permissionName));
    }

    public Optional<User> getById(Long userId) {
        return userRepo.findById(userId);
    }

    public Optional<User> getByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public void addPermissionToLoggedInUser(String permissionName) {
        Permission permission;
        Optional<Permission> permissionOpt = permissionRepo.findByName(permissionName);
        if (permissionOpt.isPresent()) {
            permission = permissionOpt.get();
        } else {
            permission = new Permission();
            permission.setName(permissionName);
            permissionRepo.saveAndFlush(permission);
        }

        Role loggedInUserRole =
                roleRepo.findByName(USER_ROLE_PREFIX + getLoggedInUser().getUsername()).get();

        loggedInUserRole.getPermissions().add(permission);

        roleRepo.save(loggedInUserRole);
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public User createUser(User user) {
        if (userRepo.findByUsername(user.getUsername()).isPresent())
            throw new UsernameTakenException(user.getUsername());

        Role role = new Role();
        role.setName(PermissionsPrefixes.USER_ROLE_PREFIX + user.getUsername());

        roleRepo.saveAndFlush(role);

        user.setId(null);
        user.setRoles(Set.of(role));

        userRepo.save(user);

        return user;
    }
}
