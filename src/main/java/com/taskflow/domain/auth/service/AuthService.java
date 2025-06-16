package com.taskflow.domain.auth.service;


import com.taskflow.domain.auth.dto.signup.SignupRequestDto;
import com.taskflow.domain.auth.dto.signup.SignupResponseDto;

public interface AuthService {
    SignupResponseDto signup(SignupRequestDto requestDto);
}
