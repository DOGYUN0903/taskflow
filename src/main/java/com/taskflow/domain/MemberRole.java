package com.taskflow.domain;

public enum MemberRole {
    ADMIN("관리자"),
    USER("사용자");

    final String role;

    MemberRole(String role) {
        this.role = role;
    }
}
