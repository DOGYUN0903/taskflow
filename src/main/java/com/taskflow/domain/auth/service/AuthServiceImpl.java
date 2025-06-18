package com.taskflow.domain.auth.service;

import com.taskflow.domain.activitylog.entity.ActivityType;
import com.taskflow.domain.auth.dto.login.LoginRequestDto;
import com.taskflow.domain.auth.dto.login.LoginResponseDto;
import com.taskflow.domain.auth.dto.signup.SignupRequestDto;
import com.taskflow.domain.auth.dto.signup.SignupResponseDto;
import com.taskflow.domain.auth.dto.withdraw.MemberWithdrawRequestDto;
import com.taskflow.domain.member.entity.Member;
import com.taskflow.domain.member.entity.UserRole;
import com.taskflow.domain.member.exception.*;
import com.taskflow.domain.member.repository.MemberRepository;
import com.taskflow.domain.member.service.MemberService;
import com.taskflow.global.annotation.LogActivity;
import com.taskflow.global.config.PasswordEncoder;
import com.taskflow.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
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

        if (requestDto.getEmail().equals(requestDto.getUsername())) {
            throw new MemberEmailUsernameDuplicateException();
        }

        //TODO: 비밀번호 암호화 로직 추가
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // member 객체 생성
        Member member = Member.builder()
                .username(requestDto.getUsername())
                .email(requestDto.getEmail())
                .password(encodedPassword)
                .name(requestDto.getName())
                .userRole(UserRole.USER)
                .is_deleted(false)
                .build();

        // 레포지토리에 member 저장
        Member savedMember = memberRepository.save(member);

        return new SignupResponseDto(savedMember);
    }

    @LogActivity(ActivityType.USER_LOGGED_IN)
    @Transactional
    @Override
    public LoginResponseDto login(LoginRequestDto requestDto) {
        // 아이디 중복 검증 + 탈퇴한 사용자 검증
        Member findMember = memberRepository.findByUsername(requestDto.getUsername())
                .filter(member -> !Boolean.TRUE.equals(member.getIs_deleted()))
                .orElseThrow(() -> new MemberNotFoundException());


        // 비밀번호 검증하기
        if (!passwordEncoder.matches(requestDto.getPassword(), findMember.getPassword())) {
            throw new MemberInvalidPasswordException();
        }

        //TODO: 토큰 생성 로직 구현
        String fullToken = jwtUtil.issueJwt(findMember.getEmail(), findMember.getUserRole());

        // Bearer  제거
        String tokenWithoutPrefix = jwtUtil.getToken(fullToken);

        // LoginResponseDto() 생성자 수정 후, 토큰 넣어주기
        return new LoginResponseDto(tokenWithoutPrefix);
    }

    @LogActivity(ActivityType.USER_WITHDRAW)
    @Transactional
    @Override
    public void withdrawMember(Long memberId, MemberWithdrawRequestDto requestDto) {
        Member findMember = memberService.findByIdOrElseThrow(memberId);

        if (!passwordEncoder.matches(requestDto.getPassword(), findMember.getPassword())) {
            throw new MemberInvalidPasswordException();
        }
        findMember.softDelete();
    }
}
