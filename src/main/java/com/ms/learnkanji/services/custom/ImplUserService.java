package com.ms.learnkanji.services.custom;

import com.ms.learnkanji.commons.MessageError;
import com.ms.learnkanji.commons.Ordering;
import com.ms.learnkanji.exceptions.AlreadyPresentException;
import com.ms.learnkanji.exceptions.InvalidInputException;
import com.ms.learnkanji.exceptions.NotFoundException;
import com.ms.learnkanji.models.Kanji;
import com.ms.learnkanji.models.Role;
import com.ms.learnkanji.models.User;
import com.ms.learnkanji.repositories.KanjiRepository;
import com.ms.learnkanji.repositories.UserRepository;
import com.ms.learnkanji.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImplUserService implements UserService {
    private final UserRepository userRepository;
    private final KanjiRepository kanjiRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ImplUserService(UserRepository userRepository,
                           KanjiRepository kanjiRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.kanjiRepository = kanjiRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUserByUsername(String username) {
        if (username == null) {
            throw new InvalidInputException(MessageError.User.USERNAME_CANNOT_BE_NULL);
        }
        if (username.isEmpty()) {
            throw new InvalidInputException(MessageError.User.USERNAME_CANNOT_BE_EMPTY);
        }

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(MessageError.User.USER_NOT_FOUND));
    }

    @Override
    public List<User> getAllUsers(Integer pageNum, Integer pageSize, Ordering ordering) {
        if (pageNum < 0) {
            throw new InvalidInputException(MessageError.Pagination.PAGE_NUM_CANNOT_BE_NEGATIVE);
        }
        if (pageSize < 1) {
            throw new InvalidInputException(MessageError.Pagination.PAGE_SIZE_CANNOT_BE_LESS_THAN_ONE);
        }
        Sort sort = (Ordering.ASC.equals(ordering)) ? Sort.by("u.username").ascending() : Sort.by("u.username").descending();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        return userRepository.findAllUser(pageable).getContent();
    }

    @Override
    public User createUser(String username, String password, Integer jlpt) {
        if (username == null) {
            throw new InvalidInputException(MessageError.User.USERNAME_CANNOT_BE_NULL);
        }
        if (username.isEmpty()) {
            throw new InvalidInputException(MessageError.User.USERNAME_CANNOT_BE_EMPTY);
        }
        if (password == null) {
            throw new InvalidInputException(MessageError.User.PASSWORD_CANNOT_BE_NULL);
        }
        if (password.isEmpty()) {
            throw new InvalidInputException(MessageError.User.PASSWORD_CANNOT_BE_EMPTY);
        }

        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            throw new AlreadyPresentException(MessageError.User.USER_ALREADY_PRESENT);
        }

        User toSave = new User(username, Role.USER, jlpt);
        toSave.setPassword(passwordEncoder.encode(password));
        return userRepository.save(toSave);
    }

    @Override
    public User userLearntKanji(String username, String kanjiValue) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(MessageError.User.USER_NOT_FOUND));
        Kanji kanji = kanjiRepository.findByValue(kanjiValue)
                .orElseThrow(() -> new NotFoundException(MessageError.Kanji.KANJI_NOT_FOUND));

        List<Kanji> kanjis = user.getKanjis();
        if (kanjis.stream().anyMatch(k -> k.getValue().equals(kanjiValue))) {
            throw new AlreadyPresentException(MessageError.Kanji.KANJI_ALREADY_LEARNT);
        }
        user.addKanji(kanji);
        // update user with new kanji
        return userRepository.save(user);
    }
}
