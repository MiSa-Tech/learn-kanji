package com.ms.learnkanji.services;

import com.ms.learnkanji.exceptions.BadRequestException;
import com.ms.learnkanji.models.Role;
import com.ms.learnkanji.models.User;
import com.ms.learnkanji.output.auth.AuthenticationResponse;
import com.ms.learnkanji.repositories.UserRepository;
import com.ms.learnkanji.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Profile("!unsecured")
public class AuthService implements IAuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(UserRepository userRepository,
                       AuthenticationManager authenticationManager,
                       CustomUserDetailsService customUserDetailService,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.customUserDetailService = customUserDetailService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthenticationResponse login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    username, password
            ));
        } catch (BadCredentialsException e) {
            throw new BadRequestException("Incorrect username or password");
        }
        final UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
        final String jwt = jwtUtil.generateToken(userDetails);
        User user = userRepository.findByUsername(username).orElse(null);
        assert user != null;
        Role role = user.getRole();
        return new AuthenticationResponse(jwt, user.getId(), user.getUsername(), role.getName());
    }

    @Override
    public void logout(String username) {
        return;
    }
}
