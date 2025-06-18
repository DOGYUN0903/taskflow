package com.taskflow.domain.auth.service;

import com.taskflow.domain.auth.dto.login.LoginRequestDto;
import com.taskflow.domain.auth.dto.login.LoginResponseDto;
import com.taskflow.domain.auth.dto.signup.SignupRequestDto;
import com.taskflow.domain.auth.dto.signup.SignupResponseDto;
import com.taskflow.domain.member.entity.Member;
import com.taskflow.domain.member.entity.UserRole;
import com.taskflow.domain.member.repository.MemberRepository;
import com.taskflow.global.config.PasswordEncoder;
import com.taskflow.global.exception.member.MemberEmailDuplicateException;
import com.taskflow.global.exception.member.MemberNotFoundException;
import com.taskflow.global.exception.member.MemberPasswordMissMatchException;
import com.taskflow.global.exception.member.MemberUsernameDuplicateException;
import com.taskflow.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

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
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        UserRole userRole = UserRole.of(requestDto.getUserRole());

        // member 객체 생성
        Member member = Member.builder()
                .username(requestDto.getUsername())
                .email(requestDto.getEmail())
                .password(encodedPassword)
                .name(requestDto.getName())
                .userRole(userRole)
                .is_deleted(false)
                .build();

        // 레포지토리에 member 저장
        Member savedMember = memberRepository.save(member);

        return new SignupResponseDto(savedMember);
    }

    @Transactional(readOnly = true)
    @Override
    public LoginResponseDto login(LoginRequestDto requestDto) {
        // 이메일 중복 검증 + 탈퇴한 사용자 검증
        Member findMember = getActiveMemberByEmail(requestDto.getEmail());

        // 비밀번호 검증하기
        if (!passwordEncoder.matches(requestDto.getPassword(), findMember.getPassword())) {
            throw new MemberPasswordMissMatchException();
        }

        //TODO: 토큰 생성 로직 구현
        String token = jwtUtil.issueJwt(findMember.getEmail(), findMember.getUserRole());

        // LoginResponseDto() 생성자 수정 후, 토큰 넣어주기
        return new LoginResponseDto(token);
    }

    // 이메일로 회원을 조회하고, 탈퇴하지 않은 사용자만 반환합니다.
    // 조건에 맞는 사용자가 없다면 예외(MemberNotFoundException)를 발생시킵니다.
    private Member getActiveMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .filter(member -> !Boolean.TRUE.equals(member.getIs_deleted())) // 조회된 회원이 '삭제되지 않은 상태'인지 확인
                .orElseThrow(() -> new MemberNotFoundException()); // 조건에 맞는 회원이 없다면 예외 발생
    }
}
