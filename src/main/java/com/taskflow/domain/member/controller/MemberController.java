package com.taskflow.domain.member.controller;

import com.taskflow.domain.member.dto.MemberProfileResponseDto;
import com.taskflow.domain.member.dto.MemberResponseDto;
import com.taskflow.domain.member.service.MemberService;
import com.taskflow.global.common.ApiResponse;
import com.taskflow.global.config.customUserDetails.Entity.CustomUserDetails;
import com.taskflow.global.response.success.MemberSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MemberProfileResponseDto>> getMemberProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getId();

        return ResponseEntity
                .status(MemberSuccess.PROFILE_READ.getStatus())
                .body(ApiResponse.success(MemberSuccess.PROFILE_READ.getMessage(), memberService.getMemberProfile(memberId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MemberResponseDto>>> getAllUsers() {
        return ResponseEntity
                .status(MemberSuccess.MEMBER_LIST_LOADED.getStatus())
                .body(ApiResponse.success(MemberSuccess.MEMBER_LIST_LOADED.getMessage(), memberService.getAllUsers()));
    }
}
