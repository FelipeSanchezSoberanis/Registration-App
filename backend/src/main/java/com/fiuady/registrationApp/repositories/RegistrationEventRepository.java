package com.fiuady.registrationApp.repositories;

import com.fiuady.registrationApp.entities.RegistrationEvent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationEventRepository extends JpaRepository<RegistrationEvent, Long> {
    Optional<RegistrationEvent> findByName(String name);

    List<RegistrationEvent> findByGroupId(Long groupId);

    Boolean existsByName(String name);

    @Query(
            "SELECT re FROM RegistrationEvent re LEFT JOIN re.registrars r "
                    + "WHERE re.group.id = :groupId "
                    + "AND (re.owner.id = :userId OR r.id = :userId)")
    List<RegistrationEvent> findByGroupForLoggedInUser(
            @Param("groupId") Long groupId, @Param("userId") Long userId);
}
