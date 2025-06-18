package com.taskflow.domain.task.exception;

import com.taskflow.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class PastDueDateException extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getErrorMessage() {
        return "마감일은 현재 시간보다 이후여야 합니다.";
    }
}
