package com.taskflow.global.exception.member;

import com.taskflow.global.exception.CustomException;
import com.taskflow.global.response.error.MemberError;
import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends CustomException {
    @Override
    public HttpStatus getStatus() {
        return MemberError.MEMBER_NOT_FOUND.getStatus();
    }

    @Override
    public String getErrorMessage() {
        return MemberError.MEMBER_NOT_FOUND.getErrorMessage();
    }
}
