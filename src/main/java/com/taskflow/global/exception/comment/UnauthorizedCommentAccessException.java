package com.taskflow.global.exception.comment;

import com.taskflow.global.exception.CustomException;
import com.taskflow.global.response.error.CommentError;
import org.springframework.http.HttpStatus;

public class UnauthorizedCommentAccessException  extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return CommentError.UNAUTHORIZED_COMMENT_ACCESS.getStatus();
    }

    @Override
    public String getErrorMessage() {
        return CommentError.UNAUTHORIZED_COMMENT_ACCESS.getErrorMessage();
    }
}
