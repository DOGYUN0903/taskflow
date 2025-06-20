package com.taskflow.global.exception;

import com.taskflow.global.common.ApiResponse;
import com.taskflow.global.exception.dashboard.StastisticException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


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

    // @Valid에서 검증값을 잘못 입력하였을 경우
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail("입력값 검증에 실패했습니다.", errors));
    }

    // null 값을 입력하였을 경우 (예: {"username" : ,
    //                             "password" : "SnakeJJangJJangMan!"}
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleJsonParseError(HttpMessageNotReadableException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail("입력값이 누락되었습니다. 확인해주세요."));
    }
}
