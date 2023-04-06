package com.fiuady.registrationApp.controllers;

import com.fiuady.registrationApp.entities.RegistrationEvent;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/groups")
public class RegistrationEventsController {

    @GetMapping("/{groupId}/registrationEvents")
    public RegistrationEvent test(@PathVariable Long groupId) {
        RegistrationEvent event = new RegistrationEvent();
        event.setName("Registration event");
        event.setStartTime(ZonedDateTime.now());
        event.setEndTime(ZonedDateTime.now().plusHours(2));

        return event;
    }
}
