package com.taskflow.domain.member.service;

import com.taskflow.domain.member.dto.MemberSignupRequestDto;
import com.taskflow.domain.member.dto.MemberSignupResponseDto;
import com.taskflow.domain.member.repository.MemberRepository;

public interface MemberService {

    MemberSignupResponseDto signup(MemberSignupRequestDto requestDto);
}
