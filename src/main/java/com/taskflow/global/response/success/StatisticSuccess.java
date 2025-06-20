package com.taskflow.global.response.success;

import org.springframework.http.HttpStatus;

public enum StatisticSuccess {
    STATISTIC_SUCCESS(HttpStatus.OK, "통계정보 전달 성공");

    private final HttpStatus status;
    private final String message;

    StatisticSuccess(HttpStatus status, String message) {
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
