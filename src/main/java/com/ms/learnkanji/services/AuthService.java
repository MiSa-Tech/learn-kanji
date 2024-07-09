package com.ms.learnkanji.services;

import com.ms.learnkanji.output.auth.AuthenticationResponse;

public interface AuthService {
    AuthenticationResponse login(String username, String password);
    void logout(String username);
}
