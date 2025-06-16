package com.taskflow.global.response.error;

import org.springframework.http.HttpStatus;

public enum MemberError {
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),
    INVALID_ROLE(HttpStatus.BAD_REQUEST, "유효하지 않은 사용자 역할입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    IS_DELETED(HttpStatus.BAD_REQUEST, "이미 탈퇴한 회원입니다.");

    private final HttpStatus status;
    private final String errorMessage;

    MemberError(HttpStatus status, String errorMessage) {
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

