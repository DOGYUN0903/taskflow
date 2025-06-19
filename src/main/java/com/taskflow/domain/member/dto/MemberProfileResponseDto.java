package com.taskflow.domain.member.dto;

import com.taskflow.domain.member.entity.Member;
import com.taskflow.domain.member.entity.UserRole;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberProfileResponseDto {

    private Long id;
    private String username; // 사용자 아이디
    private String name; // 사용자의 이름, 닉네임 x
    private String email;
    private UserRole role;
    private LocalDateTime createdAt;


    public MemberProfileResponseDto(Long id, String name, String username, String email, UserRole role, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
    }
}
