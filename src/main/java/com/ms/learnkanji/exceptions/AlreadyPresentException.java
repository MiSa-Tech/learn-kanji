package com.ms.learnkanji.exceptions;

public class AlreadyPresentException extends AbstractGraphQLException {

    public AlreadyPresentException(String message) {
        super(message);
    }
}
