package com.ms.learnkanji.services;

import com.ms.learnkanji.commons.MessageError;
import com.ms.learnkanji.exceptions.AlreadyPresentException;
import com.ms.learnkanji.exceptions.InvalidInputException;
import com.ms.learnkanji.exceptions.NotFoundException;
import com.ms.learnkanji.models.Kanji;
import com.ms.learnkanji.models.User;
import com.ms.learnkanji.repositories.KanjiRepository;
import com.ms.learnkanji.repositories.UserRepository;
import com.ms.learnkanji.services.custom.ImplUserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private KanjiRepository kanjiRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    void setUp() {
        // initialize the service
        userService = new ImplUserService(userRepository, kanjiRepository, passwordEncoder);
    }

    @Test
    void whenFindByUsername_thenReturnUser() {
        // given
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        // when
        Mockito.when(userRepository.findByUsername("test")).thenReturn(java.util.Optional.of(user));

        // then
        User found = userService.getUserByUsername("test");
        Assertions.assertNotNull(found);
    }

    @Test
    void whenFindByEmptyUsername_thenThrowException() {
        Assertions.assertThrows(InvalidInputException.class, () -> {
            userService.getUserByUsername(null);
        })
                .getMessage()
                .contains(MessageError.User.USERNAME_CANNOT_BE_NULL);
    }

    @Test
    void whenFindAllUsers_thenReturnUsers() {
        // given
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        // when
        Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Order.asc("username")));
        Mockito.when(userRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(user)));
        // then
        List<User> found = userService.getAllUsers(0, 1);
        Assertions.assertNotNull(found);
    }

    @Test
    void whenCreateUser_DuplicateUsername_thenThrowException() {
        // given
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        // when
        Mockito.when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));

        // then
        Assertions.assertThrows(AlreadyPresentException.class, () -> {
            userService.createUser("test", "test", 5);
        })
                .getMessage()
                .contains(MessageError.User.USER_ALREADY_PRESENT);
    }

    @Test
    void whenCreateUser_thenReturnUser() {
        // given
        User user = new User();
        user.setUsername("test1");
        user.setPassword("test");
        // when
        Mockito.when(userRepository.findByUsername("test")).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        // then
        Assertions.assertNotNull(userService.createUser("test", "test", 5));
    }

    @Test
    void whenUserLearnKanji_thenReturnUser() {
        // given
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        // when
        Mockito.lenient().when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        Mockito.lenient().when(kanjiRepository.findByValue("一")).thenReturn(Optional.of(new Kanji("一", null, null, null,
                5, List.of("one"), null, null)));
        Mockito.lenient().when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        // then
        User found = userService.userLearntKanji("test", "一");
        Assertions.assertNotNull(found);
    }

    @Test
    void whenUserLearnKanji_UserNotFound_thenThrowException() {
        // given
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        // when
        Mockito.when(userRepository.findByUsername("test")).thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(NotFoundException.class, () -> {
            userService.userLearntKanji("test", "一");
        })
                .getMessage()
                .contains(MessageError.User.USER_NOT_FOUND);
    }

    @Test
    void whenUserLearnKanji_KanjiNotFound_thenThrowException() {
        // given
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        // when
        Mockito.when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        Mockito.when(kanjiRepository.findByValue("一")).thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(NotFoundException.class, () -> {
            userService.userLearntKanji("test", "一");
        })
                .getMessage()
                .contains(MessageError.Kanji.KANJI_NOT_FOUND);
    }

    @Test
    void whenUserLearnKanji_UserAlreadyLearntKanji_thenThrowException() {
        // given
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.getKanjis().add(new Kanji("一", null, null, null,
                5, List.of("one"), null, null));
        // when
        Mockito.when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        Mockito.when(kanjiRepository.findByValue("一")).thenReturn(Optional.of(new Kanji("一", null, null, null,
                5, List.of("one"), null, null)));

        // then
        Assertions.assertThrows(AlreadyPresentException.class, () -> {
            userService.userLearntKanji("test", "一");
        })
                .getMessage()
                .contains(MessageError.Kanji.KANJI_ALREADY_LEARNT);
    }
}
