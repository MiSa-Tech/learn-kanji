package com.ms.learnkanji.commons;

public class MessageError {
    public class Vocabulary {
        public static final String ORIGINAL_CANNOT_BE_NULL = "Original value cannot be null";
        public static final String FURIGANA_CANNOT_BE_NULL = "Furigana cannot be null";
        public static final String MEANING_CANNOT_BE_NULL = "Meaning cannot be null";
        public static final String VOCABULARY_CANNOT_BE_FOUND = "Vocabulary cannot be found";
        public static final String JLPT_LEVEL_CANNOT_BE_LESS_THAN_ONE_OR_GREATER_THAN_FIVE = "JLPT level cannot be less than 1 or greater than 5";
    }

    public class Kanji {
        public static final String VALUE_CANNOT_BE_NULL = "Value cannot be null";
        public static final String MEANING_CANNOT_BE_NULL = "Meaning cannot be null";
        public static final String KANJI_ALREADY_PRESENT = "Kanji already present";
        public static final String KANJI_NOT_FOUND = "Kanji not found";
        public static final String KANJI_ALREADY_LEARNT = "Kanji already learnt";
        public static final String JLPT_LEVEL_CANNOT_BE_LESS_THAN_ONE_OR_GREATER_THAN_FIVE = "JLPT level cannot be less than 1 or greater than 5";
        public static final String STROKES_NUMBER_NEGATIVE = "Strokes number invalid";
        public static final String GRADE_INVALID = "Grade value invalid";
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

    public class Pagination {
        public static final String PAGE_NUM_CANNOT_BE_NEGATIVE = "Page number cannot be smaller than 0";
        public static final String PAGE_SIZE_CANNOT_BE_LESS_THAN_ONE = "Page size cannot be smaller than 1";
    }
}
