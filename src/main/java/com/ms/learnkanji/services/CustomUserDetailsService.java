package com.ms.learnkanji.services;

import com.ms.learnkanji.commons.MessageError;
import com.ms.learnkanji.models.User;
import com.ms.learnkanji.models.CustomUserDetails;
import com.ms.learnkanji.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = userRepository.findAll();
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(MessageError.Auth.USERNAME_NOT_FOUND));
    }
}
