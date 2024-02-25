package com.example.agora.member.dto;

import com.example.agora.member.entity.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDto {
    String email; // 멤버 아이디 역할을 할 이메일
    Role role; // 멤버 역할
}
