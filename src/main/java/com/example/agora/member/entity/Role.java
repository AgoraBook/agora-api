package com.example.agora.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    DEFAULT("DEFAULT", "일반 계정"),
    MASTER("MASTER", "마스터 계정");

    private final String role;
    private final String title;
}