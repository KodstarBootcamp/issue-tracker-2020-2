package com.kodstar.issuetracker.api;


import com.kodstar.issuetracker.auth.User;
import com.kodstar.issuetracker.dto.UserDTO;
import com.kodstar.issuetracker.entity.VerificationToken;
import com.kodstar.issuetracker.eventlistener.OnRegistrationCompleteEvent;
import com.kodstar.issuetracker.service.ConfirmUserService;
import com.kodstar.issuetracker.service.UserService;
import com.kodstar.issuetracker.utils.impl.FromUserToUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@CrossOrigin("*")
public class RegsitrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ConfirmUserService confirmUserService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private FromUserToUserDTO fromUserToUserDTO;


    @PostMapping("/users/sign-up")
    public ResponseEntity registerUserAccount(@Valid @NonNull @RequestBody UserDTO userDto,
                                              HttpServletRequest request) {


        if (userService.findByEmail(userDto.getEmail()) != null) {
            return new ResponseEntity("Email has already registered", HttpStatus.BAD_REQUEST);
        } else if (userService.findByUsername(userDto.getUsername()) != null) {
            return new ResponseEntity("Username has already registered", HttpStatus.BAD_REQUEST);
        } else {
            User userSaved = userService.registerNewUserAccount(userDto);

            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(userSaved,
                    request.getLocale(), appUrl));

            UserDTO savedUserDTO = fromUserToUserDTO.convert(userSaved);

            return new ResponseEntity(savedUserDTO, HttpStatus.OK);
        }
    }


    @GetMapping("/confirm/{token}")
    public ResponseEntity confirmRegistration( @PathVariable("token") String token) {

        VerificationToken verificationToken = confirmUserService.findByToken(token);

        if (verificationToken == null) {
            return new ResponseEntity("Invalid Token", HttpStatus.BAD_REQUEST);
        }

        if (verificationToken.isExpired()) {
            return new ResponseEntity("Confirmation mail timed out", HttpStatus.BAD_REQUEST);
        }

        User user = userService.findByEmail(verificationToken.getUser().getEmail());
        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        return new ResponseEntity("User approved. You can login", HttpStatus.OK);

    }
}