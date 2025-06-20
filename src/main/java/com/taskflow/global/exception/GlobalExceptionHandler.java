package com.taskflow.global.exception;

import com.taskflow.global.common.ApiResponse;
import com.taskflow.global.exception.dashboard.StastisticException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {

    // 커스텀 예외 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<String>> handleCustomException(CustomException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ApiResponse.fail(e.getErrorMessage()));
    }

    //통계 예외처리
    @ExceptionHandler(StastisticException.class)
    public ResponseEntity<ApiResponse<String>> handleStastisticException(StastisticException e) {
        return ResponseEntity
                .status(e.getStatus().value())
                .body(ApiResponse.fail(e.getMessage()));
    }
}
