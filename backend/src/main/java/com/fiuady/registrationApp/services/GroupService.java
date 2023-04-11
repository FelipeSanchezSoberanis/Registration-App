package com.fiuady.registrationApp.services;

import static com.fiuady.registrationApp.utils.PermissionsPrefixes.DELETE_GROUP_PREFIX;

import com.fiuady.registrationApp.entities.Group;
import com.fiuady.registrationApp.exceptions.GroupNameTakenException;
import com.fiuady.registrationApp.exceptions.GroupNotFoundException;
import com.fiuady.registrationApp.exceptions.InsufficientPermissionsException;
import com.fiuady.registrationApp.repositories.GroupRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired private GroupRepository groupRepository;
    @Autowired private UserService userService;

    // private boolean loggedInUserParticipatesInGroup(Group group) {
    //     return group.getParticipants().contains(userService.getLoggedInUser());
    // }

    public Group createNewGroup(Group group) {
        boolean groupNameAlreadyExists = groupRepository.findByName(group.getName()).isPresent();
        if (groupNameAlreadyExists) throw new GroupNameTakenException(group.getName());

        group.setId(null);
        group.setOwner(userService.getLoggedInUser());
        group.setRegistrationEvents(new HashSet<>());

        groupRepository.saveAndFlush(group);

        userService.addPermissionToLoggedInUser(DELETE_GROUP_PREFIX + group.getId());

        return group;
    }

    public void deleteById(Long groupId) {
        Optional<Group> groupOpt = groupRepository.findById(groupId);

        if (groupOpt.isEmpty()) throw new GroupNotFoundException(groupId);

        boolean loggedInUserCanDeleteGroup =
                userService.loggedInUserHasPermission(DELETE_GROUP_PREFIX + groupId);
        if (!loggedInUserCanDeleteGroup)
            throw new InsufficientPermissionsException(
                    "You don't have the permission to delete this group");

        groupRepository.delete(groupOpt.get());
    }

    // public Group getById(Long groupId) {
    //     Optional<Group> groupOpt = groupRepository.findById(groupId);

    //     if (groupOpt.isEmpty()) throw new GroupNotFoundException(groupId);

    //     Group group = groupOpt.get();

    //     if (!loggedInUserOwnsGroup(group) && !loggedInUserParticipatesInGroup(group))
    //         throw new InsufficientPermissionsException(
    //                 "You are not the owner nor a participant in this group");

    //     return group;
    // }

    public List<Group> getAllForLoggedInUser() {
        Long loggedInUserId = userService.getLoggedInUser().getId();
        List<Group> loggedInUserGroups =
                groupRepository.findByOwnerIdOrParticipantsId(loggedInUserId, loggedInUserId);
        return loggedInUserGroups;
    }
}
