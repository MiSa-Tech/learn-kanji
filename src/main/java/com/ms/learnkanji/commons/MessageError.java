package com.ms.learnkanji.commons;

public class MessageError {
    public class Kanji {
        public static final String VALUE_CANNOT_BE_NULL = "Value cannot be null";
        public static final String MEANING_CANNOT_BE_NULL = "Meaning cannot be null";
        public static final String KANJI_ALREADY_PRESENT = "Kanji already present";
        public static final String KANJI_NOT_FOUND = "Kanji not found";
        public static final String KANJI_ALREADY_LEARNT = "Kanji already learnt";
    }

    public class User {
        public static final String USER_ALREADY_PRESENT = "User already present";
        public static final String USERNAME_CANNOT_BE_NULL = "Username cannot be null";
        public static final String USERNAME_CANNOT_BE_EMPTY = "Username cannot be empty";
        public static final String PASSWORD_CANNOT_BE_NULL = "Password cannot be null";
        public static final String PASSWORD_CANNOT_BE_EMPTY = "Password cannot be empty";
        public static final String USER_NOT_FOUND = "User not found";
    }

    public class Auth {
        public static final String USERNAME_OR_PASSWORD_INVALID = "Incorrect username or password";
        public static final String USERNAME_NOT_FOUND = "Username not found";
    }
}
