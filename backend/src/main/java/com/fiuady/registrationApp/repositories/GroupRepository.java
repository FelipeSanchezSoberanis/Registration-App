package com.fiuady.registrationApp.repositories;

import com.fiuady.registrationApp.entities.Group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByOwnerId(Long id);

    Optional<Group> findByName(String name);
}
