package com.fiuady.registrationApp.services;

import com.fiuady.registrationApp.entities.RegistrationEvent;
import com.fiuady.registrationApp.exceptions.GroupNotFoundException;
import com.fiuady.registrationApp.exceptions.RegistrationEventNameTakenException;
import com.fiuady.registrationApp.repositories.GroupRepository;
import com.fiuady.registrationApp.repositories.RegistrationEventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationEventService {

    @Autowired private UserService userService;
    @Autowired private RegistrationEventRepository registrationEventRepository;
    @Autowired private GroupRepository groupRepository;

    public RegistrationEvent createRegistrationEvent(
            Long groupId, RegistrationEvent registrationEvent) {

        if (groupRepository.findById(groupId).isEmpty()) throw new GroupNotFoundException(groupId);
        if (registrationEventRepository.findByName(registrationEvent.getName()).isPresent())
            throw new RegistrationEventNameTakenException(registrationEvent.getName());

        registrationEvent.setOwner(userService.getLoggedInUser());

        registrationEventRepository.saveAndFlush(registrationEvent);

        return registrationEvent;
    }

    public List<RegistrationEvent> getByGroupId(Long groupId) {
        if (groupRepository.findById(groupId).isEmpty()) throw new GroupNotFoundException(groupId);

        return registrationEventRepository.findByGroupId(groupId);
    }
}
