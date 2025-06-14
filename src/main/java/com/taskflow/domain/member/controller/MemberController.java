package com.taskflow.domain.member.controller;

import com.taskflow.domain.member.dto.MemberSignupRequestDto;
import com.taskflow.domain.member.dto.MemberSignupResponseDto;
import com.taskflow.domain.member.service.MemberService;
import com.taskflow.global.common.ApiResponse;
import com.taskflow.global.response.success.MemberSuccess;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입 API
     * @param requestDto
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<MemberSignupResponseDto>> signup(@Valid @RequestBody MemberSignupRequestDto requestDto) {
        return ResponseEntity
                .status(MemberSuccess.SIGN_UP.getStatus())
                .body(ApiResponse.success(MemberSuccess.SIGN_UP.getMessage(), memberService.signup(requestDto)));
    }
}
