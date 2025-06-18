package com.taskflow.domain.task.exception;

import com.taskflow.global.exception.CustomException;
import com.taskflow.global.response.error.TaskError;
import org.springframework.http.HttpStatus;

public class AssigneeNotFoundException extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return TaskError.ASSIGNEE_NOT_FOUND.getStatus();
    }

    @Override
    public String getErrorMessage() {
        return TaskError.ASSIGNEE_NOT_FOUND.getErrorMessage();
    }
}
