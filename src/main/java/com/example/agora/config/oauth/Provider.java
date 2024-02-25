package com.example.agora.config.oauth;

public enum Provider {
    GOOGLE,
    KAKAO,
    NAVER
    ;

    public static Provider from(String value) {
        switch (value) {
            case "google":
                return GOOGLE;
            case "kakao":
                return KAKAO;
            case "naver":
                return NAVER;
            default:
                throw new IllegalArgumentException("Unsupported provider: " + value);
        }
    }
}
