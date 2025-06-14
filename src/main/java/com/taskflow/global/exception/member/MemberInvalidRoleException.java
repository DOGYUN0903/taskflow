package com.taskflow.global.exception.member;

import com.taskflow.global.exception.CustomException;
import com.taskflow.global.response.error.MemberError;
import org.springframework.http.HttpStatus;

public class MemberInvalidRoleException extends CustomException {
    @Override
    public HttpStatus getStatus() {
        return MemberError.INVALID_ROLE.getStatus();
    }

    @Override
    public String getErrorMessage() {
        return MemberError.INVALID_ROLE.getErrorMessage();
    }
}
