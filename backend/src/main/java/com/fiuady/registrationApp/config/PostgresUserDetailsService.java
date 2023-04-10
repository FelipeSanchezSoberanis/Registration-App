package com.fiuady.registrationApp.config;

import com.fiuady.registrationApp.entities.User;
import com.fiuady.registrationApp.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PostgresUserDetailsService implements UserDetailsService {

    @Autowired private UserRepository userRepo;

    @Override
    @Transactional
    public AppUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepo.findByUsername(username);

        if (userOpt.isEmpty())
            throw new UsernameNotFoundException(String.format("User %s not found", username));

        return new AppUserDetails().buildFromUser(userOpt.get());
    }
}
