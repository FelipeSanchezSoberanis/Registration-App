package com.fiuady.registrationApp.repositories;

import com.fiuady.registrationApp.entities.GroupSummary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupSummaryRepository extends JpaRepository<GroupSummary, Long> {
    public List<GroupSummary> findAllByOwnerIdOrParticipantsId(Long ownerId, Long participantId);
}
