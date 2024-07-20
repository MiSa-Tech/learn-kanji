package com.ms.learnkanji.controllers;

import com.ms.learnkanji.commons.MessageError;
import com.ms.learnkanji.exceptions.AlreadyPresentException;
import com.ms.learnkanji.exceptions.NotFoundException;
import com.ms.learnkanji.models.Role;
import com.ms.learnkanji.models.User;
import com.ms.learnkanji.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

@GraphQlTest(UserController.class)
class UserControllerUnitTest {
    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private UserService userService;

    @Test
    void whenFindByUsername_thenReturnUser() {
        // given
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setRole(Role.USER);
        // when
        BDDMockito.given(userService.getUserByUsername("test")).willReturn(user);
        // then
        // language=GraphQL
        String document = """
            query {
                findUserByUsername(username: "test") {
                    username
                    role
                }
            }
        """;

        graphQlTester.document(document)
                .execute()
                .path("findUserByUsername.username").entity(String.class).isEqualTo("test")
                .path("findUserByUsername.role").entity(String.class).isEqualTo(Role.USER.getName());
    }

    @Test
    void whenFindByUsername_shouldThrowNotFoundException() {
        // given
        String username = "test";
        // when
        BDDMockito.given(userService.getUserByUsername(username)).willThrow(new NotFoundException(MessageError.User.USER_NOT_FOUND));
        // then
        // language=GraphQL
        String document = """
            query {
                findUserByUsername(username: "test") {
                    username
                    role
                }
            }
        """;

        graphQlTester.document(document)
                .execute()
                .errors()
                .satisfy(errors -> {
                    Assertions.assertEquals(1, errors.size());
                    Assertions.assertEquals(MessageError.User.USER_NOT_FOUND, errors.get(0).getMessage());
                });
    }

    @Test
    void whenFindByEmptyUsername_shouldThrowInvalidInputException() {
        // given
        String username = "";
        // then
        // language=GraphQL
        String document = """
            query {
                findUserByUsername(username: "") {
                    username
                    role
                }
            }
        """;

        graphQlTester.document(document)
                .execute()
                .errors()
                .satisfy(errors -> {
                    Assertions.assertEquals(1, errors.size());
                    Assertions.assertEquals(MessageError.User.USERNAME_CANNOT_BE_EMPTY, errors.get(0).getMessage());
                });
    }

    @Test
    void whenCreateUser_thenReturnUser() {
        // given
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setRole(Role.USER);
        // when
        BDDMockito.given(userService.createUser("test", "test", 5)).willReturn(user);
        // then
        // language=GraphQL
        String document = """
            mutation {
                createUser(username: "test", password: "test", jlpt: 5) {
                    username
                    role
                }
            }
        """;

        graphQlTester.document(document)
                .execute()
                .path("createUser.username").entity(String.class).isEqualTo("test")
                .path("createUser.role").entity(String.class).isEqualTo(Role.USER.getName());
    }

    @Test
    void whenCreateUser_shouldThrowAlreadyPresentException() {
        // given
        String username = "test";
        // when
        BDDMockito.given(userService.createUser(username, "test", 5)).willThrow(new AlreadyPresentException(MessageError.User.USER_ALREADY_PRESENT));
        // then
        // language=GraphQL
        String document = """
            mutation {
                createUser(username: "test", password: "test", jlpt: 5) {
                    username
                    role
                }
            }
        """;

        graphQlTester.document(document)
                .execute()
                .errors()
                .satisfy(errors -> {
                    Assertions.assertEquals(1, errors.size());
                    Assertions.assertEquals(MessageError.User.USER_ALREADY_PRESENT, errors.get(0).getMessage());
                });
    }

    @Test
    void whenCreateUser_shouldThrowInvalidInputException() {
        // given
        String username = "";
        // then
        // language=GraphQL
        String document = """
            mutation {
                createUser(username: "", password: "test", jlpt: 5) {
                    username
                    role
                }
            }
        """;

        graphQlTester.document(document)
                .execute()
                .errors()
                .satisfy(errors -> {
                    Assertions.assertEquals(1, errors.size());
                    Assertions.assertEquals(MessageError.User.USERNAME_CANNOT_BE_EMPTY, errors.get(0).getMessage());
                });
    }

    @Test
    void whenUserLearnKanji_thenReturnUser() {
        // given
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setRole(Role.USER);
        // when
        BDDMockito.given(userService.userLearntKanji("test", "一")).willReturn(user);
        // then
        // language=GraphQL
        String document = """
            mutation {
                userLearntKanji(username: "test", kanji: "一") {
                    username
                    role
                }
            }
        """;

        graphQlTester.document(document)
                .execute()
                .path("userLearntKanji.username").entity(String.class).isEqualTo("test")
                .path("userLearntKanji.role").entity(String.class).isEqualTo(Role.USER.getName());
    }

    @Test
    void whenUserLearnKanji_shouldThrowNotFoundException() {
        // given
        String username = "test";
        // when
        BDDMockito.given(userService.userLearntKanji(username, "一")).willThrow(new NotFoundException(MessageError.User.USER_NOT_FOUND));
        // then
        // language=GraphQL
        String document = """
            mutation {
                userLearnKanji(username: "test", kanji: "一") {
                    username
                    role
                }
            }
        """;

        graphQlTester.document(document)
                .execute()
                .errors()
                .satisfy(errors -> {
                    Assertions.assertEquals(1, errors.size());
                    Assertions.assertEquals(MessageError.User.USER_NOT_FOUND, errors.get(0).getMessage());
                });
    }

    @Test
    void whenUserLearnKanji_shouldThrowAlreadyPresentException() {
        // given
        String kanji = "一";
        // when
        BDDMockito.given(userService.userLearntKanji("test", kanji)).willThrow(new AlreadyPresentException(MessageError.Kanji.KANJI_ALREADY_LEARNT));
        // then
        // language=GraphQL
        String document = """
            mutation {
                userLearnKanji(username: "test", kanji: "一") {
                    username
                    role
                }
            }
        """;

        graphQlTester.document(document)
                .execute()
                .errors()
                .satisfy(errors -> {
                    Assertions.assertEquals(1, errors.size());
                    Assertions.assertEquals(MessageError.Kanji.KANJI_ALREADY_LEARNT, errors.get(0).getMessage());
                });
    }
}
