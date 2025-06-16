package com.taskflow.global.response.success;

import org.springframework.http.HttpStatus;

public enum AuthSuccess {
    SIGN_UP(HttpStatus.CREATED, "회원가입에 성공하였습니다.");

    private final HttpStatus status;
    private final String message;

    AuthSuccess(HttpStatus status, String message) {
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
