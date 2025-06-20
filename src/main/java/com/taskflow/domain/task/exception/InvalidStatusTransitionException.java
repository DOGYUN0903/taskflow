package com.taskflow.domain.task.exception;

import com.taskflow.global.exception.CustomException;
import com.taskflow.global.response.error.TaskError;
import org.springframework.http.HttpStatus;

/**
 * 일정 상태(TaskStatus)가 유효한 흐름이 아닐 때 발생하는 예외
 * TaskError.INVALID_STATUS_TRANSITION 오류 코드와 메시지를 응답에 포함
 */
public class InvalidStatusTransitionException extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return TaskError.INVALID_STATUS_TRANSITION.getStatus();
    }

    @Override
    public String getErrorMessage() {
        return TaskError.INVALID_STATUS_TRANSITION.getErrorMessage();
    }
}
