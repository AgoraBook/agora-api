package com.example.agora.config.oauth;

import com.example.agora.config.jwt.AuthUtils;
import com.example.agora.config.jwt.Token;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthUtils authUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String url = makeRedirectUrl(authUtils.createToken());

        if (response.isCommitted()) {
            logger.debug("응답이 이미 커밋된 상태입니다. " + url + "로 리다이렉트하도록 바꿀 수 없습니다.");
            return;
        }
        getRedirectStrategy().sendRedirect(request, response, url);
    }

    private String makeRedirectUrl(Token token) {
        return UriComponentsBuilder.fromUriString("http://localhost:3000/oauth2/redirect")
                .queryParam("accessToken", token.getAccessToken())
                .queryParam("refreshToken", token.getRefreshToken())
                .queryParam("email", token.getEmail())
                .build().toUriString();
    }
}