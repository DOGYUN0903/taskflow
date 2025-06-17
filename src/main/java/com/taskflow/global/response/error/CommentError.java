package com.taskflow.global.response.error;

import org.springframework.http.HttpStatus;

public enum CommentError {
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."),
    UNAUTHORIZED_COMMENT_ACCESS(HttpStatus.FORBIDDEN, "댓글에 대한 권한이 없습니다."),
    CONTENT_EMPTY(HttpStatus.BAD_REQUEST, "댓글 내용이 비어 있습니다.");

    private final HttpStatus status;
    private final String errorMessage;

    CommentError(HttpStatus status, String errorMessage) {
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
