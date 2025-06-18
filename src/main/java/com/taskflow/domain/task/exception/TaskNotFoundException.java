package com.taskflow.domain.task.exception;

import com.taskflow.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class TaskNotFoundException extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getErrorMessage() {
        return "해당 일정(Task)을 찾을 수 없습니다.";
    }
}
