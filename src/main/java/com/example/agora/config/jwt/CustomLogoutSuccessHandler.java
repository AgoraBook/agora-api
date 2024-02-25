package com.example.agora.config.jwt;

import com.example.agora.config.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    private final JwtUtils jwtUtils;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        // 1. Request Header에서 JWT 추출
        String token = jwtUtils.resolveJWT(request);
        log.info("Request to {}: token={}", request.getRequestURI(), token);

        // 2. validateJWT로 토큰 유효성 검사
        if (jwtUtils.validateToken(token)) {
            // 2-1. 토큰이 유효할 경우 토큰 블랙리스트
            jwtUtils.deleteRefreshTokenByEmail(jwtUtils.getEmailFromToken(token));
            jwtUtils.setBlackList(token);
        }
    }
}
