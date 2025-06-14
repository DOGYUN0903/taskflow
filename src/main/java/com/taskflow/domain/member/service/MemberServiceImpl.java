package com.taskflow.domain.member.service;

import com.taskflow.domain.member.dto.MemberSignupRequestDto;
import com.taskflow.domain.member.dto.MemberSignupResponseDto;
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
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public MemberSignupResponseDto signup(MemberSignupRequestDto requestDto) {

        // 1. 이메일 중복 체크
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new MemberEmailDuplicateException();
        }

        // 닉네임 중복 체크(닉네임 유니크키로 지정이 되어있음)
        if (memberRepository.existsByUsername(requestDto.getUsername())) {
            throw new MemberUsernameDuplicateException();
        }

        // 2. 비밀번호 암호화
        //TODO: 비밀번호 암호화 하는 로직 추가

        // 3. Member객체 생성
        UserRole userRole = UserRole.of(requestDto.getUserRole());

        Member member = Member.builder()
                .username(requestDto.getUsername())
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .name(requestDto.getName())
                .userRole(userRole)
                .build();

        // 4. Repository에 Member를 save하기
        Member savedMember = memberRepository.save(member);

        // 5. 응답객체 생성
        return new MemberSignupResponseDto(savedMember);
    }
}
