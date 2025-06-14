package com.taskflow.global.response.success;

import com.taskflow.global.common.BaseCode;
import org.springframework.http.HttpStatus;

public enum MemberSuccess implements BaseCode {
    SIGN_UP(HttpStatus.CREATED, "회원가입에 성공하였습니다.");

    private final HttpStatus status;
    private final String message;

    MemberSuccess(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
