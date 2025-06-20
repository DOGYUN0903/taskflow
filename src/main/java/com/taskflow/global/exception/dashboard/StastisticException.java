package com.taskflow.global.exception.dashboard;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class StastisticException extends RuntimeException {
    private final HttpStatus status;

    public StastisticException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
