package com.taskflow.domain.task.exception;

import com.taskflow.global.exception.CustomException;
import com.taskflow.global.response.error.TaskError;
import org.springframework.http.HttpStatus;

public class TaskNotFoundException extends CustomException {

    private final TaskError error;

    public TaskNotFoundException() {
        this.error = TaskError.TASK_NOT_FOUND;
    }

    public TaskNotFoundException(TaskError error) {
        this.error = error;
    }

    @Override
    public HttpStatus getStatus() {
        return error.getStatus();
    }

    @Override
    public String getErrorMessage() {
        return error.getErrorMessage();
    }
}
