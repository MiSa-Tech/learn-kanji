package com.ms.learnkanji.services;

import com.ms.learnkanji.commons.MessageError;
import com.ms.learnkanji.exceptions.AlreadyPresentException;
import com.ms.learnkanji.exceptions.InvalidInputException;
import com.ms.learnkanji.exceptions.NotFoundException;
import com.ms.learnkanji.models.Kanji;
import com.ms.learnkanji.models.User;
import com.ms.learnkanji.repositories.ICustomUserRepository;
import com.ms.learnkanji.repositories.KanjiRepository;
import com.ms.learnkanji.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final KanjiRepository kanjiRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       KanjiRepository kanjiRepository) {
        this.userRepository = userRepository;
        this.kanjiRepository = kanjiRepository;
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
    public User createUser(String username, Integer jlpt) {
        if (username == null) {
            throw new InvalidInputException("Username cannot be null");
        }
        if (username.isEmpty()) {
            throw new InvalidInputException("Username cannot be empty");
        }

        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            throw new AlreadyPresentException(MessageError.USER_ALREADY_PRESENT);
        }

        User toSave = new User(username, jlpt);
        return userRepository.save(toSave);
    }

    @Override
    public User userLearntKanji(String username, String kanjiValue) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(MessageError.USER_NOT_FOUND));
        Kanji kanji = kanjiRepository.findByValue(kanjiValue)
                .orElseThrow(() -> new NotFoundException(MessageError.KANJI_NOT_FOUND));

        List<Kanji> kanjis = user.getKanjis();
        if (kanjis.contains(kanji)) {
            throw new AlreadyPresentException(MessageError.KANJI_ALREADY_LEARNT);
        }
        user.addKanji(kanji);
        // update user with new kanji
        return userRepository.save(user);
    }
}
