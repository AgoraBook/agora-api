package com.example.agora.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_DEFAULT("ROLE_DEFAULT", "일반 계정"),
    ROLE_MASTER("ROLE_MASTER", "마스터 계정");

    private final String role;
    private final String title;
}
