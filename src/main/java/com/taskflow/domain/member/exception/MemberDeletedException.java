package com.taskflow.domain.member.exception;

import com.taskflow.global.exception.CustomException;
import com.taskflow.global.response.error.MemberError;
import org.springframework.http.HttpStatus;

public class MemberDeletedException extends CustomException {
    @Override
    public HttpStatus getStatus() {
        return MemberError.IS_DELETED.getStatus();
    }

    @Override
    public String getErrorMessage() {
        return MemberError.IS_DELETED.getErrorMessage();
    }
}
