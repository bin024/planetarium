package com.planetarium.planetarium.service;

import java.util.Optional;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planetarium.planetarium.exceptions.AuthenticationFailed;
import com.planetarium.planetarium.exceptions.EntityNull;
import com.planetarium.planetarium.models.User;
import com.planetarium.planetarium.repository.UserDao;


@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getUserByUsername(String name) {

        Optional<User> possibleUser = this.userDao.getUserByUsername(name);

        if (possibleUser.isPresent()) {
            return possibleUser.get();
        } else {
            throw new EntityNull("User not found.");
        }

    }

    public String authenticatePassword(String password, String inputPassword) {

        if (password.equals(inputPassword)) {
            return "Log in successful";
        }
        else {
            throw new AuthenticationFailed("Incorrect password.");
        }
    }

    public String createUser(User user) throws PSQLException{

        Optional<User> possibleUser = this.userDao.getUserByUsername(user.getUsername());

        if (possibleUser.isPresent()) {
            throw new PSQLException("Username taken, please select another.", null);
        }

        if (user.getUsername() != null && user.getPassword() != null) {
            this.userDao.createUser(user.getUsername(), user.getPassword());
            return "User created successfully";
        }
        else {
            throw new AuthenticationFailed("Invalid username and password.");
        }

    }
    
}
