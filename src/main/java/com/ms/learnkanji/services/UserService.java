package com.ms.learnkanji.services;

import com.ms.learnkanji.commons.MessageError;
import com.ms.learnkanji.exceptions.AlreadyPresentException;
import com.ms.learnkanji.exceptions.InvalidInputException;
import com.ms.learnkanji.exceptions.NotFoundException;
import com.ms.learnkanji.models.Kanji;
import com.ms.learnkanji.models.Role;
import com.ms.learnkanji.models.User;
import com.ms.learnkanji.repositories.KanjiRepository;
import com.ms.learnkanji.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final KanjiRepository kanjiRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       KanjiRepository kanjiRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.kanjiRepository = kanjiRepository;
        this.passwordEncoder = passwordEncoder;
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
                .orElseThrow(() -> new NotFoundException(MessageError.USER_NOT_FOUND));
    }

    @Override
    public List<User> getAllUsers(Integer pageNum, Integer pageSize) {
        Sort sort = Sort.by(Sort.Order.asc("username"));
        return userRepository.findAll(PageRequest.of(pageNum, pageSize, sort)).toList();
    }

    @Override
    public User createUser(String username, String password, Integer jlpt) {
        if (username == null) {
            throw new InvalidInputException("Username cannot be null");
        }
        if (username.isEmpty()) {
            throw new InvalidInputException("Username cannot be empty");
        }
        if (password == null) {
            throw new InvalidInputException("Password cannot be null");
        }
        if (password.isEmpty()) {
            throw new InvalidInputException("Password cannot be empty");
        }

        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            throw new AlreadyPresentException(MessageError.USER_ALREADY_PRESENT);
        }

        User toSave = new User(username, Role.USER, jlpt);
        toSave.setPassword(passwordEncoder.encode(password));
        return userRepository.save(toSave);
    }

    @Override
    public User userLearntKanji(String username, String kanjiValue) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(MessageError.USER_NOT_FOUND));
        Kanji kanji = kanjiRepository.findByValue(kanjiValue)
                .orElseThrow(() -> new NotFoundException(MessageError.KANJI_NOT_FOUND));

        List<Kanji> kanjis = user.getKanjis();
        if (kanjis.stream().anyMatch(k -> k.getValue().equals(kanjiValue))) {
            throw new AlreadyPresentException(MessageError.KANJI_ALREADY_LEARNT);
        }
        user.addKanji(kanji);
        // update user with new kanji
        return userRepository.save(user);
    }
}
