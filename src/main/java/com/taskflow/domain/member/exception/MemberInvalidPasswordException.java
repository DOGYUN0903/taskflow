package com.taskflow.domain.member.exception;

import com.taskflow.global.exception.CustomException;
import com.taskflow.global.response.error.MemberError;
import org.springframework.http.HttpStatus;

public class MemberInvalidPasswordException extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return MemberError.INVALID_PASSWORD.getStatus();
    }

    @Override
    public String getErrorMessage() {
        return MemberError.INVALID_PASSWORD.getErrorMessage();
    }
}
