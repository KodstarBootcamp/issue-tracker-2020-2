package com.kodstar.issuetracker.api;


import com.kodstar.issuetracker.auth.User;
import com.kodstar.issuetracker.dto.LabelDTO;
import com.kodstar.issuetracker.dto.UserDTO;
import com.kodstar.issuetracker.repo.UserRepository;
import com.kodstar.issuetracker.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("users")
public class UserController {

    private UserService userService;
    private UserRepository userDAO;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserService userService, UserRepository userDAO,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.userDAO = userDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDAO.save(user);
    }

    @GetMapping()
    public ResponseEntity<Set<UserDTO>> getAllUsers() {
        return new ResponseEntity(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<Set<UserDTO>> getUserById(@PathVariable("userId") Long userId) {
        return new ResponseEntity(userService.getUserById(userId), HttpStatus.OK);
    }

}