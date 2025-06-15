package com.taskflow.domain.auth.dto.signup;

import com.taskflow.domain.member.entity.Member;
import com.taskflow.domain.member.entity.UserRole;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignupResponseDto {

    private Long id;
    private String username;
    private String email;
    private String name;
    private UserRole userRole;
    private LocalDateTime createdAt;

    public SignupResponseDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.name = member.getName();
        this.userRole = member.getUserRole();
        this.createdAt = member.getCreatedAt();
    }
}
