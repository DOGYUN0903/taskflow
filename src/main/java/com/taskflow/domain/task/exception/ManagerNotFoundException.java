package com.taskflow.domain.task.exception;

import com.taskflow.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ManagerNotFoundException extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getErrorMessage() {
        return "담당자로 지정된 사용자가 존재하지 않습니다.";
    }
}
