package com.ms.learnkanji.controllers;

import com.ms.learnkanji.models.Kanji;
import com.ms.learnkanji.models.User;
import com.ms.learnkanji.models.Vocabulary;
import com.ms.learnkanji.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> findAllUsers(@Argument Integer pageNum,
                                   @Argument Integer pageSize) {
        return userService.getAllUsers(pageNum, pageSize);
    }

    @MutationMapping
    public User createUser(@Argument("username") String username,
                           @Argument("password") String password,
                           @Argument("jlpt") Integer jlpt) {
        return userService.createUser(username, password, jlpt);
    }

    @MutationMapping
    public User userLearntKanji(@Argument("username") String username,
                                @Argument("kanji") String kanjiValue) {
        return userService.userLearntKanji(username, kanjiValue);
    }

    @SchemaMapping(typeName = "User", field = "role")
    public String role(User user) {
        return user.getRole().getName();
    }

    @SchemaMapping(typeName = "User", field = "LEARNT_KANJI")
    public List<Kanji> listKanjisLearntByUser(User user) {
        return user.getKanjis();
    }

    @SchemaMapping(typeName = "User", field = "LEARNT_VOCABULARY")
    public List<Vocabulary> listVocabulariesLearntByUser(User user) {
        return user.getVocabularies();
    }
}
