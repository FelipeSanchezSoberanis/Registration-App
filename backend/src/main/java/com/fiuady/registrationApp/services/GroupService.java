package com.fiuady.registrationApp.services;

import com.fiuady.registrationApp.entities.Group;
import com.fiuady.registrationApp.exceptions.GroupNameTakenException;
import com.fiuady.registrationApp.exceptions.GroupNotFoundException;
import com.fiuady.registrationApp.exceptions.InsufficientPermissionsException;
import com.fiuady.registrationApp.repositories.GroupRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired private GroupRepository groupRepository;
    @Autowired private UserService userService;

    private boolean loggedInUserOwnsGroup(Group group) {
        return group.getOwner().equals(userService.getLoggedInUser());
    }

    private boolean loggedInUserParticipatesInGroup(Group group) {
        return group.getParticipants().contains(userService.getLoggedInUser());
    }

    public Group createNewGroup(Group group) {
        group.setOwner(userService.getLoggedInUser());

        if (groupRepository.findByName(group.getName()).isPresent())
            throw new GroupNameTakenException(group.getName());

        return groupRepository.save(group);
    }

    public List<Group> getLoggedInUserGroups() {
        Long id = userService.getLoggedInUser().getId();
        return groupRepository.findByOwnerIdOrParticipantsId(id, id);
    }

    public void deleteById(Long groupId) {
        Optional<Group> groupOpt = groupRepository.findById(groupId);

        if (groupOpt.isEmpty()) throw new GroupNotFoundException(groupId);

        if (!loggedInUserOwnsGroup(groupOpt.get()))
            throw new InsufficientPermissionsException(
                    "You cant delete a group you are not the owner of");

        groupRepository.delete(groupOpt.get());
    }

    public Group getById(Long groupId) {
        Optional<Group> groupOpt = groupRepository.findById(groupId);

        if (groupOpt.isEmpty()) throw new GroupNotFoundException(groupId);

        Group group = groupOpt.get();

        if (!loggedInUserOwnsGroup(group) && !loggedInUserParticipatesInGroup(group))
            throw new InsufficientPermissionsException(
                    "You are not the owner nor a participant in this group");

        return group;
    }
}
