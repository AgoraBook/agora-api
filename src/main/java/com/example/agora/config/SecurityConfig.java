package com.example.agora.config;

import com.example.agora.config.jwt.CustomLogoutSuccessHandler;
import com.example.agora.config.jwt.JwtFilter;
import com.example.agora.config.oauth.CustomOAuth2UserService;
import com.example.agora.config.oauth.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final OAuth2SuccessHandler successHandler;
    private final CustomLogoutSuccessHandler logoutSuccessHandler;
    private final CustomOAuth2UserService oAuth2UserService;

    private String[] permitList = {
            "/login/oauth2/code",
            "/login/oauth2/code/*"
    };

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(HttpBasicConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> {
                    auth
                            .requestMatchers("/**").hasRole("ROLE_MASTER")
                            .requestMatchers(permitList).permitAll()
                            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                            .anyRequest().authenticated();
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // jwtFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
                //.addFilterBefore(new JwtExceptionFilter(), JwtFilter.class) // JwtExceptionFilter를 JwtFilter 앞에 추가

                .logout(log -> log
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler(logoutSuccessHandler)
                )
                // OAuth 2.0 로그인 설정 시작
                .oauth2Login( (oauth2Login) -> oauth2Login
                                .redirectionEndpoint( redirection -> redirection
                                                .baseUri("/auth/login/oauth2/code") // OAuth 2.0 공급자로부터 코드가 리다이렉션될 때의 기본 URI
                                ) // 리다이렉션 엔드포인트 설정
                                .successHandler(successHandler) // OAuth 2.0 로그인 성공 시의 핸들러를 설정
                                .userInfoEndpoint((endpoint)->endpoint
                                        .userService(oAuth2UserService)) // 사용자 정보 엔드포인트에서 사용할 사용자 서비스를 설정
                );

        return http.build();
    }
}