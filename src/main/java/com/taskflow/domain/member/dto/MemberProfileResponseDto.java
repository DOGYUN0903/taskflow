package com.taskflow.domain.member.dto;

import com.taskflow.domain.member.entity.Member;
import com.taskflow.domain.member.entity.UserRole;
import lombok.Getter;

@Getter
public class MemberProfileResponseDto {

    private String name; // 사용자의 이름, 닉네임 x
    private String email;
    private UserRole userRole;

    public MemberProfileResponseDto(String name, String email, UserRole userRole) {
        this.name = name;
        this.email = email;
        this.userRole = userRole;
    }
}
