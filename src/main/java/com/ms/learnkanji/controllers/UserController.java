package com.ms.learnkanji.controllers;

import com.ms.learnkanji.models.User;
import com.ms.learnkanji.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @QueryMapping
    public User findUserByUsername(@Argument String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @QueryMapping
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @MutationMapping
    public User createUser(@Argument String username) {
        User user = new User();
        user.setUsername(username);
        return userRepository.saveAll(List.of(user)).get(0);
    }
}
