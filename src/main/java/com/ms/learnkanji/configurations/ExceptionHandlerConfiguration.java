package com.ms.learnkanji.configurations;

import com.ms.learnkanji.controllers.base.ResponseUtil;
import com.ms.learnkanji.controllers.base.RestData;
import com.ms.learnkanji.exceptions.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerConfiguration.class);

    private final MessageSource messageSource;

    public ExceptionHandlerConfiguration(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = {BadRequestException.class})
    protected ResponseEntity<RestData<?>> handleBadRequestException(BadRequestException ex) {
        List<String> messages = new ArrayList<>();
        LOGGER.error(ex.getMessage(), ex);
        String message = ex.getMessage();
        messages.add(message);
        return ResponseUtil.responseError(HttpStatus.BAD_REQUEST, messages);
    }
}
