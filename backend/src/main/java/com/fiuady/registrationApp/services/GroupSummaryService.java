package com.fiuady.registrationApp.services;

import com.fiuady.registrationApp.entities.GroupSummary;
import com.fiuady.registrationApp.entities.User;
import com.fiuady.registrationApp.repositories.GroupSummaryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupSummaryService {

    @Autowired private GroupSummaryRepository groupSummaryRepository;
    @Autowired private UserService userService;

    public List<GroupSummary> getAll() {
        return groupSummaryRepository.findAll();
    }

    public List<GroupSummary> getAllForLoggedInUser() {
        User loggedInUser = userService.getLoggedInUser();

        return groupSummaryRepository.findAllByOwnerIdOrParticipantsId(
                loggedInUser.getId(), loggedInUser.getId());
    }
}
