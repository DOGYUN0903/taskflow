package com.taskflow.global.response.success;

import org.springframework.http.HttpStatus;

public enum MemberSuccess {
    PROFILE_READ(HttpStatus.OK, "회원 정보를 성공적으로 조회했습니다.");

    private final HttpStatus status;
    private final String message;

    MemberSuccess(HttpStatus status, String message) {
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
