package com.taskflow.domain.task.exception;

import com.taskflow.global.exception.CustomException;
import com.taskflow.global.response.error.TaskError;
import org.springframework.http.HttpStatus;

/**
 * 일정 상태 변경을 요청한 사용자가 권한이 없을 때 발생하는 예외
 * TaskError.UNAUTHORIZED_STATUS_CHANGE 오류 코드와 메시지를 응답에 포함
 */
public class UnauthorizedStatusChangeException extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return TaskError.UNAUTHORIZED_STATUS_CHANGE.getStatus();
    }

    @Override
    public String getErrorMessage() {
        return TaskError.UNAUTHORIZED_STATUS_CHANGE.getErrorMessage();
    }
}
