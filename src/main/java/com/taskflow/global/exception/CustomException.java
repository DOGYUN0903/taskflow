package com.taskflow.global.exception;


import org.springframework.http.HttpStatus;

public abstract class CustomException extends RuntimeException {

    public abstract HttpStatus getStatus();

    public abstract String getErrorMessage();
}
