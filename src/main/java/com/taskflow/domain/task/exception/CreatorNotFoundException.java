package com.taskflow.domain.task.exception;

import com.taskflow.global.exception.CustomException;
import com.taskflow.global.response.error.TaskError;
import org.springframework.http.HttpStatus;

/**
 * 생성자(creator)를 찾을 수 없을 때 발생하는 예외
 * TaskError.CREATOR_NOT_FOUND 오류 코드와 메시지를 응답에 포함
 */
public class CreatorNotFoundException extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return TaskError.CREATOR_NOT_FOUND.getStatus();
    }

    @Override
    public String getErrorMessage() {
        return TaskError.CREATOR_NOT_FOUND.getErrorMessage();
    }
}
