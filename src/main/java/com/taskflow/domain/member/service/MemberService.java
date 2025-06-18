package com.taskflow.domain.member.service;

import com.taskflow.domain.member.dto.MemberProfileResponseDto;
import com.taskflow.domain.auth.dto.withdraw.MemberWithdrawRequestDto;
import com.taskflow.domain.member.entity.Member;

public interface MemberService {

    MemberProfileResponseDto getMemberProfile(Long memberId);

    Member findByIdOrElseThrow(Long memberId);

    Member findByUsernameOrElseThrow(String username);
}
