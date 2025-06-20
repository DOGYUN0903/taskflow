package com.taskflow.domain.task.exception;

import com.taskflow.global.exception.CustomException;
import com.taskflow.global.response.error.TaskError;
import org.springframework.http.HttpStatus;

/**
 * 담당자(assignee)를 찾을 수 없을 때 발생하는 예외
 * TaskError.ASSIGNEE_NOT_FOUND 오류 코드와 메시지를 응답에 포함
 */
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
