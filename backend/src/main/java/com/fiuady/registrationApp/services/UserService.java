package com.fiuady.registrationApp.services;

import com.fiuady.registrationApp.config.AppUserDetails;
import com.fiuady.registrationApp.entities.User;
import com.fiuady.registrationApp.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired private UserRepository userRepo;

    public User getLoggedInUser() {
        AppUserDetails userDetails =
                (AppUserDetails)
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = new User();
        user.setUsername(userDetails.getUsername());
        user.setId(userDetails.getId());

        return user;
    }

    public Optional<User> getById(Long userId) {
        return userRepo.findById(userId);
    }

    public Optional<User> getByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
