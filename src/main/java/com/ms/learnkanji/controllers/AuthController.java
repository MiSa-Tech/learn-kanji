package com.ms.learnkanji.controllers;

import com.ms.learnkanji.controllers.base.ResponseUtil;
import com.ms.learnkanji.input.auth.AuthenticationRequest;
import com.ms.learnkanji.output.auth.AuthenticationResponse;
import com.ms.learnkanji.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Profile("secured")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        return ResponseUtil.responseOk("Login successful",
                authService.login(request.getUsername(), request.getPassword()));
    }
}
