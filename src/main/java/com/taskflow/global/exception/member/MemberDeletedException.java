package com.taskflow.global.exception.member;

import com.taskflow.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class MemberDeletedException extends CustomException {
    @Override
    public HttpStatus getStatus() {
        return null;
    }

    @Override
    public String getErrorMessage() {
        return "";
    }
}
