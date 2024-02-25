package com.example.agora.config.oauth;

import com.example.agora.member.entity.Member;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;

public class MemberPrincipal implements OAuth2User, UserDetails {

    private Member member;
    private List<GrantedAuthority> authorities;
    private Map<String, Object> oauthUserAttributes;

    private MemberPrincipal(Member member, List<GrantedAuthority> authorities,
                            Map<String, Object> oauthUserAttributes) {
        this.member = member;
        this.authorities = authorities;
        this.oauthUserAttributes = oauthUserAttributes;
    }

    /**
     * OAuth2 로그인시 사용
     */
    public static MemberPrincipal create(Member member, Map<String, Object> oauthUserAttributes) {
        return new MemberPrincipal(member, List.of(() -> "ROLE_DEFAULT"), oauthUserAttributes);
    }

    public static MemberPrincipal create(Member member) {
        return new MemberPrincipal(member, List.of(() -> "ROLE_DEFAULT"), new HashMap<>());
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return String.valueOf(member.getEmail());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(oauthUserAttributes);
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public <A> A getAttribute(String email) {
        return (A) oauthUserAttributes.get(email);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableList(authorities);
    }

    @Override
    public String getName() {
        return String.valueOf(member.getEmail());
    }
}