package com.kodstar.issuetracker.service.impl;

import com.kodstar.issuetracker.auth.User;
import com.kodstar.issuetracker.dto.UserDTO;

import com.kodstar.issuetracker.entity.VerificationToken;
import com.kodstar.issuetracker.repo.ConfirmUserRepository;
import com.kodstar.issuetracker.repo.UserRepository;
import com.kodstar.issuetracker.service.UserService;
import com.kodstar.issuetracker.utils.impl.FromUserDTOToUser;
import com.kodstar.issuetracker.utils.impl.FromUserToUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final FromUserToUserDTO fromUserToUserDTO;
    private final FromUserDTOToUser fromUserDTOToUser;
    private final ConfirmUserRepository confirmUserRepository;

    public UserServiceImpl(UserRepository userRepository, FromUserToUserDTO fromUserToUserDTO, FromUserDTOToUser fromUserDTOToUser, ConfirmUserRepository confirmUserRepository) {
        this.userRepository = userRepository;
        this.fromUserToUserDTO = fromUserToUserDTO;
        this.fromUserDTOToUser = fromUserDTOToUser;
        this.confirmUserRepository = confirmUserRepository;
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
    public User registerNewUserAccount(UserDTO userDTO) {

        if (emailExist(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email already in use!");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        String password2 = bCryptPasswordEncoder.encode(userDTO.getPassword());
        user.setPassword(password2);
        user.setEmail(userDTO.getEmail());

        return userRepository.save(user);
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        user.setVerification(token);
        userRepository.save(user);
        myToken.setExpiryDate(VerificationToken.EXPIRATION);
        confirmUserRepository.save(myToken);
    }

}
