package com.taskflow.domain.auth.service;


import com.taskflow.domain.auth.dto.login.LoginRequestDto;
import com.taskflow.domain.auth.dto.login.LoginResponseDto;
import com.taskflow.domain.auth.dto.signup.SignupRequestDto;
import com.taskflow.domain.auth.dto.signup.SignupResponseDto;
import com.taskflow.domain.auth.dto.withdraw.MemberWithdrawRequestDto;

public interface AuthService {
    SignupResponseDto signup(SignupRequestDto requestDto);

    LoginResponseDto login(LoginRequestDto requestDto);

    void withdrawMember(Long memberId, MemberWithdrawRequestDto requestDto);
}
