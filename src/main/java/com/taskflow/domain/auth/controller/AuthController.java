package com.taskflow.domain.auth.controller;

import com.taskflow.domain.activitylog.entity.ActivityType;
import com.taskflow.domain.auth.dto.login.LoginRequestDto;
import com.taskflow.domain.auth.dto.login.LoginResponseDto;
import com.taskflow.domain.auth.dto.signup.SignupRequestDto;
import com.taskflow.domain.auth.dto.signup.SignupResponseDto;
import com.taskflow.domain.auth.service.AuthService;
import com.taskflow.domain.auth.dto.withdraw.MemberWithdrawRequestDto;
import com.taskflow.global.annotation.LogActivity;
import com.taskflow.global.common.ApiResponse;
import com.taskflow.global.config.customUserDetails.Entity.CustomUserDetails;
import com.taskflow.global.response.success.AuthSuccess;
import com.taskflow.global.response.success.MemberSuccess;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입 API
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<SignupResponseDto>> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        return ResponseEntity
                .status(AuthSuccess.SIGN_UP.getStatus())
                .body(ApiResponse.success(AuthSuccess.SIGN_UP.getMessage(), authService.signup(requestDto)));
    }

    // 로그인 API
    @LogActivity(ActivityType.USER_LOGGED_IN)
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto requestDto) {
        return ResponseEntity
                .status(AuthSuccess.LOGIN.getStatus())
                .body(ApiResponse.success(AuthSuccess.LOGIN.getMessage(), authService.login(requestDto)));
    }

    // 회원 탈퇴
    @LogActivity(ActivityType.USER_WITHDRAW)
    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<Void>> withdrawMember(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                            @RequestBody MemberWithdrawRequestDto requestDto) {
        Long memberId = userDetails.getId();
        authService.withdrawMember(memberId, requestDto);
        return ResponseEntity
                .status(MemberSuccess.WITHDRAW.getStatus())
                .body(ApiResponse.success(MemberSuccess.WITHDRAW.getMessage()));
    }
}
