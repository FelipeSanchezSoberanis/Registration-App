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

    public Group createNewGroup(Group group) {
        group.setOwner(userService.getLoggedInUser());

        if (groupRepository.findByName(group.getName()).isPresent())
            throw new GroupNameTakenException(group.getName());

        return groupRepository.save(group);
    }

    public List<Group> getLoggedInUserGroups() {
        return groupRepository.findByOwnerId(userService.getLoggedInUser().getId());
    }

    public void deleteById(Long groupId) {
        Optional<Group> groupOpt = groupRepository.findById(groupId);

        if (groupOpt.isEmpty()) throw new GroupNotFoundException(groupId);

        if (!userService.getLoggedInUser().equals(groupOpt.get().getOwner()))
            throw new InsufficientPermissionsException(
                    "You cant delete a group you are not the owner of");

        groupRepository.delete(groupOpt.get());
    }
}
