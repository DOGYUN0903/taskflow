package com.taskflow.domain.task.exception;

import com.taskflow.global.exception.CustomException;
import com.taskflow.global.response.error.TaskError;
import org.springframework.http.HttpStatus;

/**
 * 유효하지 않은 일정 상태(TaskStatus) 값이 전달될 때 발생하는 예외
 * TaskError.INVALID_STATUS 오류 코드와 메시지를 응답에 포함
 */
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
