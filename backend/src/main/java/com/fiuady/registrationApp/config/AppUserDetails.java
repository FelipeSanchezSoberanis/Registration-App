package com.fiuady.registrationApp.config;

import com.fiuady.registrationApp.entities.Permission;
import com.fiuady.registrationApp.entities.Role;
import com.fiuady.registrationApp.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AppUserDetails implements UserDetails {

    private Collection<SimpleGrantedAuthority> authorities;
    private String password;
    private String username;
    private Long id;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public AppUserDetails buildFromUser(User user) {
        Set<Role> userRoles = user.getRoles();
        Set<Permission> userPermissions = new HashSet<>();

        userRoles.forEach(r -> userPermissions.addAll(r.getPermissions()));

        Set<SimpleGrantedAuthority> userAuthSet =
                userPermissions.stream()
                        .map(p -> new SimpleGrantedAuthority(p.getName()))
                        .collect(Collectors.toSet());

        AppUserDetails userDetails =
                new AppUserDetailsBuilder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .authorities(userAuthSet)
                        .id(user.getId())
                        .build();

        return userDetails;
    }
}
