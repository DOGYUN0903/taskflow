package com.taskflow.domain.member.exception;

import com.taskflow.global.exception.CustomException;
import com.taskflow.global.response.error.MemberError;
import org.springframework.http.HttpStatus;

public class MemberEmailUsernameDuplicateException extends CustomException {
    @Override
    public HttpStatus getStatus() {
        return MemberError.EMAIL_USERNAME_DUPLICATE.getStatus();
    }

    @Override
    public String getErrorMessage() {
        return MemberError.EMAIL_USERNAME_DUPLICATE.getErrorMessage();
    }
}
