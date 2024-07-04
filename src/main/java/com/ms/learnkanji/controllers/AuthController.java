package com.ms.learnkanji.controllers;

import com.ms.learnkanji.output.auth.AuthenticationResponse;
import com.ms.learnkanji.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@Profile("secured")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @MutationMapping
    public AuthenticationResponse login(@Argument String username, @Argument String password) {
        return authService.login(username, password);
    }
}
