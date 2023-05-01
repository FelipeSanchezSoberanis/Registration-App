package com.fiuady.registrationApp.services;

import com.fiuady.registrationApp.entities.Group;
import com.fiuady.registrationApp.entities.RegistrationEvent;
import com.fiuady.registrationApp.exceptions.GroupNotFoundException;
import com.fiuady.registrationApp.exceptions.RegistrationEventNameTakenException;
import com.fiuady.registrationApp.repositories.GroupRepository;
import com.fiuady.registrationApp.repositories.RegistrationEventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationEventService {

    @Autowired private UserService userService;
    @Autowired private RegistrationEventRepository registrationEventRepository;
    @Autowired private GroupRepository groupRepository;

    public RegistrationEvent createRegistrationEvent(
            Long groupId, String name, ZonedDateTime startTime, ZonedDateTime endTime) {
        Optional<Group> groupOpt = groupRepository.findById(groupId);
        if (groupOpt.isEmpty()) throw new GroupNotFoundException(groupId);
        if (registrationEventRepository.existsByName(name))
            throw new RegistrationEventNameTakenException(name);

        RegistrationEvent event = new RegistrationEvent();
        event.setName(name);
        event.setStartTime(startTime);
        event.setEndTime(endTime);
        event.setOwner(userService.getLoggedInUser());
        event.setCreatedAt(ZonedDateTime.now());
        event.setGroup(groupOpt.get());

        registrationEventRepository.saveAndFlush(event);

        return event;
    }

    public List<RegistrationEvent> getByGroupId(Long groupId) {
        if (groupRepository.findById(groupId).isEmpty()) throw new GroupNotFoundException(groupId);

        return registrationEventRepository.findByGroupId(groupId);
    }

    public List<RegistrationEvent> getInGroupForLoggedInUser(Long groupId) {
        if (!groupRepository.existsById(groupId)) throw new GroupNotFoundException(groupId);
        return registrationEventRepository.findByGroupForLoggedInUser(
                groupId, userService.getLoggedInUser().getId());
    }
}
