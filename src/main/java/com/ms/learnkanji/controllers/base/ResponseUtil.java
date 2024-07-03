package com.ms.learnkanji.controllers.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseUtil {
    public static ResponseEntity<?> responseOk(String message, Object data) {
        return ResponseEntity.ok(new RestData<>(message, data));
    }

    public static ResponseEntity<RestData<?>> responseError(HttpStatus status, List<String> messages) {
        return new ResponseEntity<>(new RestData<>(RestStatus.ERROR, status.getReasonPhrase(), messages, null), status);
    }
}
