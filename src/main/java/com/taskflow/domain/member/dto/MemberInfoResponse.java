package com.taskflow.domain.member.dto;

import com.taskflow.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoResponse {

    private Long id;
    private String username;
    private String name;
    private String email;

    public static MemberInfoResponse from(Member member) {
        return new MemberInfoResponse(
                member.getId(),
                member.getUsername(),
                member.getName(),
                member.getEmail()
        );
    }
}
