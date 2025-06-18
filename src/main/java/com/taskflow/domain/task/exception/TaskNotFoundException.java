package com.taskflow.domain.task.exception;

import com.taskflow.global.exception.CustomException;
import com.taskflow.global.response.error.TaskError;
import org.springframework.http.HttpStatus;

/**
 * 일정(Task)을 찾을 수 없을 때 발생하는 예외
 * TaskError 내 정의된 상세 사유에 따라 메시지 반환
 */
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
