package com.taskflow.global.config.customUserDetails.Service;

import com.taskflow.domain.member.entity.Member;
import com.taskflow.domain.member.repository.MemberRepository;
import com.taskflow.global.config.customUserDetails.Entity.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(optionalMember.isEmpty())
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        Member member = optionalMember.get();

        return new CustomUserDetails(member);
    }
}
