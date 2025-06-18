package com.taskflow.domain.task.exception;

import com.taskflow.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class CreatorNotFoundException extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getErrorMessage() {
        return "생성자 이메일에 해당하는 사용자를 찾을 수 없습니다.";
    }
}
