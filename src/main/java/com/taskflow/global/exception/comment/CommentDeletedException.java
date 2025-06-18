package com.taskflow.global.exception.comment;

import com.taskflow.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class CommentDeletedException extends CustomException {
    @Override
    public HttpStatus getStatus() {
        return null;
    }

    @Override
    public String getErrorMessage() {
        return "";
    }
}
