package com.fiuady.registrationApp.repositories;

import com.fiuady.registrationApp.entities.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    List<Role> findByPermissionsName(String permissionName);

    Boolean existsByName(String name);
}
