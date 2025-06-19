package com.taskflow.domain.member.service;


import com.taskflow.domain.member.dto.MemberProfileResponseDto;
import com.taskflow.domain.member.dto.MemberResponseDto;
import com.taskflow.domain.member.entity.Member;
import com.taskflow.domain.member.repository.MemberRepository;
import com.taskflow.domain.member.exception.MemberDeletedException;
import com.taskflow.domain.member.exception.MemberNotFoundException;
import com.taskflow.global.config.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    @Override
    public MemberProfileResponseDto getMemberProfile(Long memberId) {

        // 탈퇴한 회원 검증
        Member findMember = findByIdOrElseThrow(memberId);

        MemberProfileResponseDto memberProfileResponseDto = memberRepository.findProfileDtoById((findMember.getId()))
                .orElseThrow(() -> new MemberNotFoundException());

        return memberProfileResponseDto;
    }

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

    @Override
    public Member findByUsernameOrElseThrow(String username) {
        Member findMember = memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException());

        if (findMember.getIs_deleted()) {
            throw new MemberDeletedException();
        }
        return findMember;
    }

    @Override
    public List<MemberResponseDto> getAllUsers() {
        List<MemberResponseDto> result = memberRepository.findAll().stream()
                .filter(member -> !member.getIs_deleted()) // 탈퇴한 유저 제외
                .map(MemberResponseDto::new)
                .toList();
        return result;
    }
}
