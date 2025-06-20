package com.taskflow.global.exception.comment;

import com.taskflow.global.exception.CustomException;
import com.taskflow.global.response.error.CommentError;
import org.springframework.http.HttpStatus;

public class CommentContentEmptyException  extends CustomException {
    @Override
    public HttpStatus getStatus() {
        return CommentError.CONTENT_EMPTY.getStatus();
    }

    @Override
    public String getErrorMessage() {
        return CommentError.CONTENT_EMPTY.getErrorMessage();
    }
}
