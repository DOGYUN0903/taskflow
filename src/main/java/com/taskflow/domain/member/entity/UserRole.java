package com.taskflow.domain.member.entity;

import com.taskflow.global.exception.member.MemberInvalidRoleException;

import java.util.Arrays;

public enum UserRole {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String role;


    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public static UserRole of(String role) {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new MemberInvalidRoleException());
    }
}
