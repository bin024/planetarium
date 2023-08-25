package com.planetarium.planetarium.controller;

import javax.servlet.http.HttpSession;

import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.planetarium.planetarium.exceptions.AuthenticationFailed;
import com.planetarium.planetarium.exceptions.EntityNull;
import com.planetarium.planetarium.models.User;
import com.planetarium.planetarium.models.UserPasswordAuthentication;
import com.planetarium.planetarium.service.UserService;

@RestController
public class AuthenticationController {

    private static Logger authenticationLogger = LoggerFactory.getLogger(AuthenticationController.class); 

    @Autowired
    private UserService userService;

    @ExceptionHandler(EntityNull.class)
    public ResponseEntity<String> entityNotFound(EntityNull e){
        authenticationLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

     //handles user input duplicate usernames to avoid 500 status codes
    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<String> sqlIssue(PSQLException e){
        authenticationLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(AuthenticationFailed.class)
    public ResponseEntity<String> authenticationFailed(AuthenticationFailed e){
        authenticationLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    //wrote the UserPasswordAuthentication class to avoid having to use String.split several times
    @PostMapping("/login")
    public ResponseEntity<String> login(HttpSession session, @RequestBody UserPasswordAuthentication body) {

        User u = userService.getUserByUsername(body.getUsername());

        //compare user input password with the password associated with username
        String message = userService.authenticatePassword(u.getPassword(), body.getPassword());

        session.setAttribute("user", u);

        authenticationLogger.info("");
        return new ResponseEntity<String>(message, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        authenticationLogger.info("");
        return new ResponseEntity<String>("Logged out sucessfully.", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody User user) throws PSQLException {
        authenticationLogger.info("");
        return new ResponseEntity<String>(this.userService.createUser(user), HttpStatus.OK);
    }

}
