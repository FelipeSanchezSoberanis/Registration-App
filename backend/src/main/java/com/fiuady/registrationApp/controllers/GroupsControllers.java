package com.fiuady.registrationApp.controllers;

import com.fiuady.registrationApp.entities.Group;
import com.fiuady.registrationApp.entities.GroupSummary;
import com.fiuady.registrationApp.services.GroupService;
import com.fiuady.registrationApp.services.GroupSummaryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupsControllers {

    @Autowired private GroupService groupService;
    @Autowired private GroupSummaryService groupSummaryService;

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        return new ResponseEntity<>(groupService.createNewGroup(group), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GroupSummary>> getLoggedInUserGroups() {
        return new ResponseEntity<>(groupSummaryService.getAllForLoggedInUser(), HttpStatus.OK);
    }

    // @GetMapping("/{groupId}")
    // public ResponseEntity<Group> getById(@PathVariable Long groupId) {
    // Group group = groupService.getById(groupId);

    // group.getOwner().setPassword(null);
    // group.getOwner().setRoles(null);

    // return new ResponseEntity<>(group, HttpStatus.OK);
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Object> deleteGroup(@PathVariable("id") Long groupId) {
    // groupService.deleteById(groupId);
    // return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    // }
}
