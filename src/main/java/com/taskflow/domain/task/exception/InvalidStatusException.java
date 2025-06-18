package com.taskflow.domain.task.exception;

import com.taskflow.global.exception.CustomException;
import com.taskflow.global.response.error.TaskError;
import org.springframework.http.HttpStatus;

public class InvalidStatusException extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return TaskError.INVALID_STATUS.getStatus();
    }

    @Override
    public String getErrorMessage() {
        return TaskError.INVALID_STATUS.getErrorMessage();
    }
}