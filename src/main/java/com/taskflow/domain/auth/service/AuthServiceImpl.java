package com.taskflow.domain.auth.service;

import com.taskflow.domain.auth.dto.signup.SignupRequestDto;
import com.taskflow.domain.auth.dto.signup.SignupResponseDto;
import com.taskflow.domain.member.entity.Member;
import com.taskflow.domain.member.entity.UserRole;
import com.taskflow.domain.member.repository.MemberRepository;
import com.taskflow.global.exception.member.MemberEmailDuplicateException;
import com.taskflow.global.exception.member.MemberUsernameDuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public SignupResponseDto signup(SignupRequestDto requestDto) {

        // 이메일 중복 체크
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new MemberEmailDuplicateException();
        }

        // 닉네임 중복 체크(닉네임은 현재 Unique key 설정)
        if (memberRepository.existsByUsername(requestDto.getUsername())) {
            throw new MemberUsernameDuplicateException();
        }

        //TODO: 비밀번호 암호화 로직 추가

        UserRole userRole = UserRole.of(requestDto.getUserRole());

        // member 객체 생성
        Member member = Member.builder()
                .username(requestDto.getUsername())
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .name(requestDto.getName())
                .userRole(userRole)
                .build();

        // 레포지토리에 member 저장
        Member savedMember = memberRepository.save(member);

        return new SignupResponseDto(savedMember);
    }
}
