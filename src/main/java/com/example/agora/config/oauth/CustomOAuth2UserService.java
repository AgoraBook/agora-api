package com.example.agora.config.oauth;

import com.example.agora.member.entity.Member;
import com.example.agora.member.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
//인가코드를 가로채서 사용자 정보를 가져와줍니다 -> Oauth2UserService -> loadUser
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {

        Map<String, Object> attributes = super.loadUser(oAuth2UserRequest).getAttributes();
        Provider provider = Provider.from(oAuth2UserRequest.getClientRegistration().getRegistrationId());

        validateAttributes(attributes);
        Member member = registerIfNewUser(attributes, provider);

        return MemberPrincipal.create(member, attributes);
    }

    private void validateAttributes(Map<String, Object> attributes) {
        if (!attributes.containsKey("email")) {
            throw new IllegalArgumentException("서드파티의 응답에 email이 존재하지 않습니다!!!");
        }
    }

    private Member registerIfNewUser(Map<String, Object> userInfoAttributes, Provider provider) {
        String email = (String) userInfoAttributes.get("email");

        Optional<Member> optionalMember = memberRepository.findByEmailAndProvider(email, provider);

        // 랜덤 비밀번호 생성
        String rawPassword = generateRandomPassword();

        // 비밀번호 암호화
        String encodedPassword = encodePassword(rawPassword);

        if (optionalMember.isPresent()) {
            return optionalMember.get();
        }

        Member member = Member.builder()
                .provider(provider)
                .email(email)
                .password(encodedPassword)
                .nickname(null)
                .age(null)
                .build();

        return memberRepository.save(member);
    }

    // 랜덤 비밀번호 생성
    public static String generateRandomPassword() {
        // 원하는 길이의 비밀번호를 생성할 수 있습니다.
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[{]}|;:,<.>/?";
        StringBuilder password = new StringBuilder();
        int length = 10; // 비밀번호 길이

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            password.append(chars.charAt(index));
        }

        return password.toString();
    }

    // 비밀번호 암호화
    public static String encodePassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
