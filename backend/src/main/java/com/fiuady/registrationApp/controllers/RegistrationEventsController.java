package com.fiuady.registrationApp.controllers;

import com.fiuady.registrationApp.entities.RegistrationEvent;
import com.fiuady.registrationApp.services.RegistrationEventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groups")
public class RegistrationEventsController {

    @Autowired private RegistrationEventService registrationEventService;

    @PostMapping("/{groupId}/registrationEvents")
    public ResponseEntity<RegistrationEvent> createRegistrationEvent(
            @PathVariable("groupId") Long groupId,
            @RequestBody RegistrationEvent registrationEvent) {
        RegistrationEvent newRegistrationEvent =
                registrationEventService.createRegistrationEvent(
                        groupId,
                        registrationEvent.getName(),
                        registrationEvent.getStartTime(),
                        registrationEvent.getEndTime());

        newRegistrationEvent.getOwner().setPassword(null);
        newRegistrationEvent.getOwner().setRoles(null);

        return new ResponseEntity<>(newRegistrationEvent, HttpStatus.CREATED);
    }
}
