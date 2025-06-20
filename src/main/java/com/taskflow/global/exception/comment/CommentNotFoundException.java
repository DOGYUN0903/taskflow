package com.taskflow.global.exception.comment;

import com.taskflow.global.exception.CustomException;
import com.taskflow.global.response.error.CommentError;
import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return CommentError.COMMENT_NOT_FOUND.getStatus();
    }

    @Override
    public String getErrorMessage() {
        return CommentError.COMMENT_NOT_FOUND.getErrorMessage();
    }
}
