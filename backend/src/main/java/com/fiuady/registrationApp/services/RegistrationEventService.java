package com.fiuady.registrationApp.services;

import com.fiuady.registrationApp.entities.Group;
import com.fiuady.registrationApp.entities.RegistrationEvent;
import com.fiuady.registrationApp.entities.User;
import com.fiuady.registrationApp.exceptions.GroupNotFoundException;
import com.fiuady.registrationApp.exceptions.InsufficientPermissionsException;
import com.fiuady.registrationApp.exceptions.RegistrationEventNameTakenException;
import com.fiuady.registrationApp.repositories.GroupRepository;
import com.fiuady.registrationApp.repositories.RegistrationEventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationEventService {

    @Autowired private UserService userService;
    @Autowired private RegistrationEventRepository registrationEventRepository;
    @Autowired private GroupRepository groupRepository;

    public RegistrationEvent createRegistrationEvent(
            Long groupId, RegistrationEvent registrationEvent) {

        Optional<Group> groupOpt = groupRepository.findById(groupId);

        if (groupOpt.isEmpty()) throw new GroupNotFoundException(groupId);
        if (registrationEventRepository.findByName(registrationEvent.getName()).isPresent())
            throw new RegistrationEventNameTakenException(registrationEvent.getName());

        User loggedInUser = userService.getLoggedInUser();

        if (!groupOpt.get().getOwner().equals(loggedInUser)
                && !groupOpt.get().getParticipants().contains(loggedInUser))
            throw new InsufficientPermissionsException(
                    "You're not the owner or a participant of this group");

        registrationEvent.setOwner(userService.getLoggedInUser());

        registrationEventRepository.saveAndFlush(registrationEvent);

        return registrationEvent;
    }

    public List<RegistrationEvent> getByGroupId(Long groupId) {
        if (groupRepository.findById(groupId).isEmpty()) throw new GroupNotFoundException(groupId);

        return registrationEventRepository.findByGroupId(groupId);
    }
}
