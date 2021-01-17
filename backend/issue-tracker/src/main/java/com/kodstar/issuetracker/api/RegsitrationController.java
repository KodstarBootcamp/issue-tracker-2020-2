package com.kodstar.issuetracker.api;


import com.kodstar.issuetracker.auth.User;
import com.kodstar.issuetracker.dto.UserDTO;
import com.kodstar.issuetracker.entity.VerificationToken;
import com.kodstar.issuetracker.eventlistener.OnRegistrationCompleteEvent;
import com.kodstar.issuetracker.service.UserService;
import com.kodstar.issuetracker.utils.impl.FromUserToUserDTO;
import org.modelmapper.internal.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;

@RestController
@CrossOrigin("*")
//@RequestMapping("register")
public class RegsitrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private FromUserToUserDTO fromUserToUserDTO;

    @Qualifier("abc")
    @Autowired
    private MessageSource messages;

    @PostMapping("/users/sign-up")
    public ResponseEntity registerUserAccount(@Valid @NonNull @RequestBody UserDTO userDto,
            HttpServletRequest request, Errors errors) {
        User userSaved =null;
        UserDTO savedUserDTO=null;

        try {
            userSaved = userService.registerNewUserAccount(userDto);

            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(userSaved,
                    request.getLocale(), appUrl));
        } catch (IllegalArgumentException uaeEx) {

        } catch (RuntimeException ex) {

        }
        savedUserDTO= fromUserToUserDTO.convert(userSaved)      ;

        return new ResponseEntity(savedUserDTO, HttpStatus.OK);
    }

    @GetMapping("/confirm/{token}")
    public String confirmRegistration(WebRequest request, @PathVariable("token")  String token) {

        Locale locale = request.getLocale();


        VerificationToken verificationToken = userService.getVerificationToken(token);
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userService.saveRegisteredUser(user);

        if (verificationToken == null) {
            return  "hello";
        }

      //  User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
       /* if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return  "hello2";
        }*/

       // user.setEnabled(true);
      //  userService.saveRegisteredUser(user);
        return  "hello3";
    }
}