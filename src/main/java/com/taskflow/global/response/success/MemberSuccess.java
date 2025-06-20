package com.taskflow.global.response.success;

import org.springframework.http.HttpStatus;

public enum MemberSuccess {
    PROFILE_READ(HttpStatus.OK, "회원 정보를 성공적으로 조회했습니다."),
    WITHDRAW(HttpStatus.OK, "성공적으로 탈퇴하였습니다."),
    MEMBER_LIST_LOADED(HttpStatus.OK, "회원들을 성공적으로 조회하였습니다.");

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
