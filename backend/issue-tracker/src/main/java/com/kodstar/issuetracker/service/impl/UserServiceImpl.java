package com.kodstar.issuetracker.service.impl;

import com.kodstar.issuetracker.auth.User;
import com.kodstar.issuetracker.dto.UserDTO;

import com.kodstar.issuetracker.entity.VerificationToken;
import com.kodstar.issuetracker.repo.UserRepository;
import com.kodstar.issuetracker.repo.VerificationTokenRepository;
import com.kodstar.issuetracker.service.UserService;
import com.kodstar.issuetracker.utils.impl.FromUserToUserDTO;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FromUserToUserDTO fromUserToUserDTO;
    private final VerificationTokenRepository tokenRepository;

    public UserServiceImpl(UserRepository userRepository, FromUserToUserDTO fromUserToUserDTO, VerificationTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.fromUserToUserDTO = fromUserToUserDTO;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Set<UserDTO> getAllUsers() {
        Set<UserDTO> userDTOSet = new HashSet<>();
        for (UserDTO userDTO :
                fromUserToUserDTO.convertAll((List<User>) userRepository.findAll())) {
            userDTOSet.add(userDTO);
        }
        return userDTOSet;
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(NoSuchElementException::new);

        UserDTO userDTO = fromUserToUserDTO.convert(user);

        return userDTO;
    }
    @Override
    public User registerNewUserAccount(UserDTO userDto) {

        if (emailExist(userDto.getEmail())) {
            throw new IllegalArgumentException("Email already in use!");
        }

        User user = new User();

        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());;
        return userRepository.save(user);
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    public User getUser(String verificationToken) {
        User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

}
