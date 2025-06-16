package com.taskflow.domain.member.service;


import com.taskflow.domain.member.dto.MemberProfileResponseDto;
import com.taskflow.domain.member.entity.Member;
import com.taskflow.domain.member.repository.MemberRepository;
import com.taskflow.global.exception.member.MemberDeletedException;
import com.taskflow.global.exception.member.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    // 회원 Id를 이용한 회원 조회 메서드
    @Override
    public Member findByIdOrElseThrow(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());

        if (findMember.getIs_deleted()) {
            throw new MemberDeletedException();
        }
        return findMember;
    }

    @Transactional(readOnly = true)
    @Override
    public MemberProfileResponseDto getMemberProfile(Long memberId) {

        MemberProfileResponseDto memberProfileResponseDto = memberRepository.findProfileDtoById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());

        return memberProfileResponseDto;
    }
}
