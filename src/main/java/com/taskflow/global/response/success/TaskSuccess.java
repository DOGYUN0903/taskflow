package com.taskflow.global.response.success;

import org.springframework.http.HttpStatus;

public enum TaskSuccess {
    TASK_CREATED_SUCCESS(HttpStatus.CREATED, "일정 생성에 성공했습니다."),
    TASK_READ_SUCCESS(HttpStatus.OK, "일정 목록 조회에 성공했습니다."),
    TASK_READ_ONE_SUCCESS(HttpStatus.OK, "일정 단건 조회에 성공했습니다."),
    TASK_UPDATED_SUCCESS(HttpStatus.OK, "일정 수정에 성공했습니다."),
    TASK_STATUS_UPDATED_SUCCESS(HttpStatus.OK, "작업 상태가 업데이트되었습니다."),
    TASK_DELETED_SUCCESS(HttpStatus.OK, "일정 삭제에 성공했습니다.");

    private final HttpStatus status;
    private final String message;

    TaskSuccess(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
