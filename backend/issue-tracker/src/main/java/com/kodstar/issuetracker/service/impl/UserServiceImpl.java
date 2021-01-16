package com.kodstar.issuetracker.service.impl;

import com.kodstar.issuetracker.auth.User;
import com.kodstar.issuetracker.dto.UserDTO;

import com.kodstar.issuetracker.entity.VerificationToken;
import com.kodstar.issuetracker.repo.UserRepository;
import com.kodstar.issuetracker.repo.VerificationTokenRepository;
import com.kodstar.issuetracker.service.UserService;
import com.kodstar.issuetracker.utils.impl.FromUserDTOToUser;
import com.kodstar.issuetracker.utils.impl.FromUserToUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    private final VerificationTokenRepository tokenRepository;

    public UserServiceImpl(UserRepository userRepository, FromUserToUserDTO fromUserToUserDTO, FromUserDTOToUser fromUserDTOToUser, VerificationTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.fromUserToUserDTO = fromUserToUserDTO;
        this.fromUserDTOToUser = fromUserDTOToUser;
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
    public User registerNewUserAccount(UserDTO userDTO) {

        if (emailExist(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email already in use!");
        }

        User user2 = new User();
        user2.setUsername(userDTO.getUsername());
        String password2 = bCryptPasswordEncoder.encode(userDTO.getPassword());
        user2.setPassword(password2);
        user2.setEmail(userDTO.getEmail());
        user2.setVerification(userDTO.getVerification());
        System.out.println(user2.getUsername());
        return userRepository.save(user2);
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    public UserDTO getUser(String verificationToken) {

        User user = tokenRepository.findByToken(verificationToken).getUser();
        return fromUserToUserDTO.convert(user);

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
