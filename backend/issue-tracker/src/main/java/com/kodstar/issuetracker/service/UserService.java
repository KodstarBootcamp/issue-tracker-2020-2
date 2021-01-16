package com.kodstar.issuetracker.service;


import com.kodstar.issuetracker.auth.User;
import com.kodstar.issuetracker.dto.IssueDTO;
import com.kodstar.issuetracker.dto.LabelDTO;
import com.kodstar.issuetracker.dto.UserDTO;
import com.kodstar.issuetracker.entity.VerificationToken;

import java.util.List;
import java.util.Set;

public interface UserService {

    Set<UserDTO> getAllUsers();

    UserDTO getUserById(Long userId);
    User registerNewUserAccount(UserDTO userDto);

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

}
