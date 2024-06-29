package com.ms.learnkanji.controllers;

import com.ms.learnkanji.models.User;
import com.ms.learnkanji.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @QueryMapping
    public User findUserByUsername(@Argument String username) {
        return userService.getUserByUsername(username);
    }

    @QueryMapping
    public List<User> findAllUsers() {
        return userService.getAllUsers();
    }

    @MutationMapping
    public User createUser(@Argument("username") String username) {
        return userService.createUser(username);
    }
}
