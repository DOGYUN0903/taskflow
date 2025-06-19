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

    public MemberInfoResponse(Member member){
        this.id = member.getId();
        this.username = member.getUsername();
        this.name = member.getName();
        this.email = member.getEmail();
    }


}
