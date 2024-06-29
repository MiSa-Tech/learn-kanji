package com.ms.learnkanji.exceptions;

import java.util.Map;

public class UserAlreadyPresentException extends AbstractGraphQLException {

    public UserAlreadyPresentException(String message) {
        super(message);
    }
}
