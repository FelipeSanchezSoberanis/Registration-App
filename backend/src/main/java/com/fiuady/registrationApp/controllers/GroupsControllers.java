package com.fiuady.registrationApp.controllers;

import com.fiuady.registrationApp.entities.Group;
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
public class GroupsControllers {

    @Autowired private GroupService groupService;

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        Group newGroup = groupService.createNewGroup(group);

        newGroup.getOwner().setPassword(null);
        newGroup.getOwner().setRoles(null);

        return new ResponseEntity<>(newGroup, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Group>> getLoggedInUserGroups() {
        List<Group> groups = groupService.getLoggedInUserGroups();

        for (Group group : groups) {
            group.getOwner().setPassword(null);
            group.getOwner().setRoles(null);
        }

        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGroup(@PathVariable("id") Long groupId) {
        groupService.deleteById(groupId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
