package com.fiuady.registrationApp.controllers;

import com.fiuady.registrationApp.entities.Group;
import com.fiuady.registrationApp.entities.User;
import com.fiuady.registrationApp.services.GroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupsController {

    @Autowired private GroupService groupService;

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        Group newGroup = groupService.createNewGroup(group.getName());
        return new ResponseEntity<>(newGroup, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Group>> getLoggedInUserGroups() {
        List<Group> loggedInUserGroups = groupService.getAllForLoggedInUser();

        for (Group group : loggedInUserGroups) {
            group.getOwner().setPassword(null);
            group.getOwner().setRoles(null);
            group.setRegistrationEvents(null);
            group.setParticipants(null);
        }

        return new ResponseEntity<>(loggedInUserGroups, HttpStatus.OK);
    }

    @PostMapping("/{groupId}/participants")
    public ResponseEntity<Object> addParticipantsToGroup(
            @PathVariable("groupId") Long groupId, @RequestBody List<User> newParticipants) {
        groupService.addParticipantsToGroup(groupId, newParticipants);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{groupId}/participants")
    public ResponseEntity<Object> removeParticipantsFromGroup(
            @PathVariable("groupId") Long groupId, @RequestBody List<User> participantsToRemove) {
        groupService.removeParticipantsFromGroup(groupId, participantsToRemove);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // @DeleteMapping("/{groupId}")
    // public ResponseEntity<Object> deleteGroup(@PathVariable("groupId") Long groupId) {
    //     groupService.deleteById(groupId);
    //     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    // }

    // @GetMapping("/{groupId}")
    // public ResponseEntity<Group> getById(@PathVariable Long groupId) {
    // Group group = groupService.getById(groupId);

    // group.getOwner().setPassword(null);
    // group.getOwner().setRoles(null);

    // return new ResponseEntity<>(group, HttpStatus.OK);
    // }
}
