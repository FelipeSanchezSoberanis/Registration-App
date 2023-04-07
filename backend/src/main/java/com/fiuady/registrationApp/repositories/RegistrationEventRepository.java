package com.fiuady.registrationApp.repositories;

import com.fiuady.registrationApp.entities.RegistrationEvent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationEventRepository extends JpaRepository<RegistrationEvent, Long> {
    Optional<RegistrationEvent> findByName(String name);

    List<RegistrationEvent> findByGroupId(Long groupId);
}
