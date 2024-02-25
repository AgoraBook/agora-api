package com.example.agora.member.entity;

import com.example.agora.config.oauth.Provider;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private Provider provider;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = true)
    private String nickname;

    @Column(name = "age", nullable = true)
    private Integer age;


}
