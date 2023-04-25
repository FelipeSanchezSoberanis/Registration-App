package com.fiuady.registrationApp.services;

import static com.fiuady.registrationApp.utils.PermissionsPrefixes.DELETE_GROUP_PREFIX;

import com.fiuady.registrationApp.entities.Group;
import com.fiuady.registrationApp.entities.Permission;
import com.fiuady.registrationApp.entities.Role;
import com.fiuady.registrationApp.entities.User;
import com.fiuady.registrationApp.exceptions.GroupNameTakenException;
import com.fiuady.registrationApp.exceptions.GroupNotFoundException;
import com.fiuady.registrationApp.exceptions.InsufficientPermissionsException;
import com.fiuady.registrationApp.repositories.GroupRepository;
import com.fiuady.registrationApp.repositories.PermissionRepository;
import com.fiuady.registrationApp.repositories.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired private GroupRepository groupRepo;
    @Autowired private UserService userService;
    @Autowired private RoleRepository roleRepo;
    @Autowired private PermissionRepository permissionRepo;

    // private boolean loggedInUserParticipatesInGroup(Group group) {
    //     return group.getParticipants().contains(userService.getLoggedInUser());
    // }

    public Group createNewGroup(String groupName) {
        if (groupRepo.existsByName(groupName)) throw new GroupNameTakenException(groupName);

        Group group = new Group();
        group.setName(groupName);
        group.setOwner(userService.getLoggedInUser());
        group.setCreatedAt(ZonedDateTime.now());

        groupRepo.saveAndFlush(group);

        return group;
    }

    public Group createNewGroup(Group group) {
        if (groupRepo.existsByName(group.getName()))
            throw new GroupNameTakenException(group.getName());

        // group.setId(null);
        // group.setOwner(userService.getLoggedInUser());
        // group.setRegistrationEvents(new HashSet<>());

        // userService.addPermissionToLoggedInUser(DELETE_GROUP_PREFIX + group.getId());

        return group;
    }

    public void deleteById(Long groupId) {
        Optional<Group> groupOpt = groupRepo.findById(groupId);

        if (groupOpt.isEmpty()) throw new GroupNotFoundException(groupId);

        String permissionName = DELETE_GROUP_PREFIX + groupId;
        boolean loggedInUserCanDeleteGroup = userService.loggedInUserHasPermission(permissionName);
        if (!loggedInUserCanDeleteGroup)
            throw new InsufficientPermissionsException(
                    "You don't have the permission to delete this group");

        groupRepo.delete(groupOpt.get());

        List<Role> rolesWithDeletePerm = roleRepo.findByPermissionsName(permissionName);
        Permission permission = permissionRepo.findByName(permissionName).get();

        for (Role role : rolesWithDeletePerm) {
            role.getPermissions().remove(permission);
        }

        roleRepo.saveAll(rolesWithDeletePerm);
        permissionRepo.delete(permission);
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
                groupRepo.findByOwnerIdOrParticipantsId(loggedInUserId, loggedInUserId);
        return loggedInUserGroups;
    }

    public void addParticipantsToGroup(Long groupId, List<User> newParticipants) {
        Optional<Group> groupOpt = groupRepo.findById(groupId);
        if (groupOpt.isEmpty()) throw new GroupNotFoundException(groupId);

        Group group = groupOpt.get();
        group.getParticipants().addAll(newParticipants);

        groupRepo.save(group);
    }

    public void removeParticipantsFromGroup(Long groupId, List<User> participantsToRemove) {
        Optional<Group> groupOpt = groupRepo.findById(groupId);
        if (groupOpt.isEmpty()) throw new GroupNotFoundException(groupId);

        Group group = groupOpt.get();
        group.getParticipants().removeAll(participantsToRemove);

        groupRepo.save(group);
    }
}
