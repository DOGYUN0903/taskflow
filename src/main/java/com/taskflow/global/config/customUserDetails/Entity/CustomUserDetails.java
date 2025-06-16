package com.taskflow.global.config.customUserDetails.Entity;

import com.taskflow.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

//인증객체: 인증 인가가 필요한 경우에 Mmeber 대신 사용합니다.
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Member member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + member.getUserRole()));
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    // 계정 고유값_이름? 아이디? 이메일? 메일을 아이디로 쓰니까 메일?
    @Override
    public String getUsername() {
        return member.getEmail();
    }

    //하단 메서드는 쓰지 않는 메서드로 비활성화 위해서 return true
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
