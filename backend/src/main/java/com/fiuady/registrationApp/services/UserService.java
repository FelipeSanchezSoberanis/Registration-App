package com.fiuady.registrationApp.services;

import com.fiuady.registrationApp.config.AppUserDetails;
import com.fiuady.registrationApp.entities.User;
import com.fiuady.registrationApp.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired private UserRepository userRepo;

    public User getLoggedInUser() {
        AppUserDetails loggedInUserDetails =
                (AppUserDetails)
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepo.findById(loggedInUserDetails.getId()).get();
    }
}
