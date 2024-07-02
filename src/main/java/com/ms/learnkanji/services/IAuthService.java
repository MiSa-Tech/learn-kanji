package com.ms.learnkanji.services;

import com.ms.learnkanji.output.auth.AuthenticationResponse;

public interface IAuthService {
    AuthenticationResponse login(String username, String password);
    void logout(String username);
}
