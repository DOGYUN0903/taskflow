package com.taskflow.global.exception.member;

import com.taskflow.global.exception.CustomException;
import com.taskflow.global.response.error.MemberError;
import org.springframework.http.HttpStatus;

public class MemberEmailDuplicateException extends CustomException {
    @Override
    public HttpStatus getStatus() {
        return MemberError.DUPLICATE_EMAIL.getStatus();
    }

    @Override
    public String getErrorMessage() {
        return MemberError.DUPLICATE_EMAIL.getErrorMessage();
    }
}
