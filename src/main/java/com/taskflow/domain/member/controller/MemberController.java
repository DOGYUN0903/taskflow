package com.taskflow.domain.member.controller;

import com.taskflow.domain.member.dto.MemberProfileResponseDto;
import com.taskflow.domain.member.service.MemberService;
import com.taskflow.global.common.ApiResponse;
import com.taskflow.global.response.success.MemberSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberProfileResponseDto>> getMemberProfile(@PathVariable("memberId") Long memberId) {
        return ResponseEntity
                .status(MemberSuccess.PROFILE_READ.getStatus())
                .body(ApiResponse.success(MemberSuccess.PROFILE_READ.getMessage(), memberService.getMemberProfile(memberId)));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponse<Void>> withdrawMember(@PathVariable("memberId") Long memberId) {
        memberService.withdrawMember(memberId);
        return ResponseEntity
                .status(MemberSuccess.WITHDRAW.getStatus())
                .body(ApiResponse.success(MemberSuccess.WITHDRAW.getMessage()));
    }
}
