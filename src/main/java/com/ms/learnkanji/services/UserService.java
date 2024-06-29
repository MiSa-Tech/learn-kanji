package com.ms.learnkanji.services;

import com.ms.learnkanji.commons.MessageError;
import com.ms.learnkanji.exceptions.InvalidInputException;
import com.ms.learnkanji.exceptions.UserAlreadyPresentException;
import com.ms.learnkanji.exceptions.UserNotFoundException;
import com.ms.learnkanji.models.User;
import com.ms.learnkanji.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByUsername(String username) {
        if (username == null) {
            throw new InvalidInputException("Username cannot be null");
        }
        if (username.isEmpty()) {
            throw new InvalidInputException("Username cannot be empty");
        }

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(MessageError.USER_NOT_FOUND));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(String username) {
        if (username == null) {
            throw new InvalidInputException("Username cannot be null");
        }
        if (username.isEmpty()) {
            throw new InvalidInputException("Username cannot be empty");
        }

        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            throw new UserAlreadyPresentException(MessageError.USER_ALREADY_PRESENT);
        }

        User toSave = new User();
        toSave.setUsername(username);
        return userRepository.save(toSave);
    }
}
