package com.fiuady.registrationApp.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Table(name = "registrationEvents")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(value = Include.NON_NULL)
public class RegistrationEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private ZonedDateTime createdAt;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private Set<String> attendeesEmails;
    @ManyToMany private Set<User> registrars;
    @ManyToOne private User owner;
    @ManyToOne private Group group;
}
