package com.taskflow.global.response.success;

import org.springframework.http.HttpStatus;

public enum CommentSuccess {
    COMMENT_CREATED(HttpStatus.CREATED, "댓글이 생성되었습니다."),
//    COMMENT_UPDATED(HttpStatus.OK, "댓글이 성공적으로 수정되었습니다."),
    COMMENT_DELETED(HttpStatus.OK, "댓글이 삭제되었습니다."),
    COMMENT_READ(HttpStatus.OK, "댓글 목록을 조회했습니다.");
    //COMMENT_SEARCH(HttpStatus.OK, "댓글이 성공적으로 검색되었습니다.");

    private final HttpStatus status;
    private final String message;

    CommentSuccess(HttpStatus status, String message) {
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
