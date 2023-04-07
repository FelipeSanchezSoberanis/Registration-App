package com.fiuady.registrationApp.controllers;

import com.fiuady.registrationApp.entities.RegistrationEvent;
import com.fiuady.registrationApp.services.RegistrationEventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/groups")
public class RegistrationEventsController {

    @Autowired private RegistrationEventService registrationEventService;

    @GetMapping("/{groupId}/registrationEvents")
    public RegistrationEvent test(@PathVariable Long groupId) {
        RegistrationEvent event = new RegistrationEvent();
        event.setName("Registration event");
        event.setStartTime(ZonedDateTime.now());
        event.setEndTime(ZonedDateTime.now().plusHours(2));

        return event;
    }

    @PostMapping("/{groupId}/registrationEvents")
    public ResponseEntity<RegistrationEvent> createRegistrationEvent(
            @PathVariable("groupId") Long groupId,
            @RequestBody RegistrationEvent registrationEvent) {
        registrationEvent =
                registrationEventService.createRegistrationEvent(groupId, registrationEvent);

        registrationEvent.getOwner().setPassword(null);
        registrationEvent.getOwner().setRoles(null);

        return new ResponseEntity<>(registrationEvent, HttpStatus.CREATED);
    }
}
