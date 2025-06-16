package com.taskflow.domain.task.exception;

import com.taskflow.global.exception.CustomException;
import org.springframework.http.HttpStatus;

/**
 * 존재하지 않는 Task를 조회/수정/삭제하려 할 때 발생하는 예외
 */
public class TaskNotFoundException extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getErrorMessage() {
        return "해당 일정을 찾을 수 없습니다.";
    }
}
