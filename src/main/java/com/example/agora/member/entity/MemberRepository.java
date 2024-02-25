package com.example.agora.member.entity;

import com.example.agora.config.oauth.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByEmailAndProvider(String email, Provider provider);
}
