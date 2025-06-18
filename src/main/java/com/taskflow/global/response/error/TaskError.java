package com.taskflow.global.response.error;

import org.springframework.http.HttpStatus;

public enum TaskError {
    TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 일정입니다."),
    TASK_NOT_FOUND_BY_SEARCH(HttpStatus.NOT_FOUND, "검색한 내용과 일치하는 일정이 없습니다."),
    TASK_NOT_FOUND_BY_ASSIGNEE(HttpStatus.NOT_FOUND, "해당 담당자의 일정이 없습니다."),
    ASSIGNEE_NOT_FOUND(HttpStatus.NOT_FOUND, "담당자를 찾을 수 없습니다."),
    CREATOR_NOT_FOUND(HttpStatus.NOT_FOUND, "작성자를 찾을 수 없습니다."),
    INVALID_STATUS(HttpStatus.BAD_REQUEST, "유효하지 않은 상태값입니다."),
    ALREADY_DELETED(HttpStatus.BAD_REQUEST, "이미 삭제된 일정입니다."),
    UNAUTHORIZED_MODIFICATION(HttpStatus.UNAUTHORIZED, "수정 권한이 없습니다.");

    private final HttpStatus status;
    private final String errorMessage;

    TaskError(HttpStatus status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
