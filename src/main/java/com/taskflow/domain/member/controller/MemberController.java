package com.taskflow.domain.member.controller;

import com.taskflow.domain.member.dto.MemberProfileResponseDto;
import com.taskflow.domain.member.service.MemberService;
import com.taskflow.global.common.ApiResponse;
import com.taskflow.global.config.customUserDetails.Entity.CustomUserDetails;
import com.taskflow.global.response.success.MemberSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<MemberProfileResponseDto>> getMemberProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getId();

        return ResponseEntity
                .status(MemberSuccess.PROFILE_READ.getStatus())
                .body(ApiResponse.success(MemberSuccess.PROFILE_READ.getMessage(), memberService.getMemberProfile(memberId)));
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<ApiResponse<Void>> withdrawMember(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getId();
        memberService.withdrawMember(memberId);
        return ResponseEntity
                .status(MemberSuccess.WITHDRAW.getStatus())
                .body(ApiResponse.success(MemberSuccess.WITHDRAW.getMessage()));
    }
}
