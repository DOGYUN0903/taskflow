package com.taskflow.domain.member.dto;

import com.taskflow.domain.member.entity.Member;
import com.taskflow.domain.member.entity.UserRole;
import lombok.Getter;

@Getter
public class MemberResponseDto {

    private Long id;
    private String email;
    private String name;
    private UserRole role;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.role = member.getUserRole();
    }
}
