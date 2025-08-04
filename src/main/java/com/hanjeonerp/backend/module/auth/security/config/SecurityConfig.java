package com.hanjeonerp.backend.module.auth.security.config;

import com.hanjeonerp.backend.module.auth.jwt.JwtAuthFilter;
import com.hanjeonerp.backend.module.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {



    private final JwtTokenProvider jwtTokenProvider;

    /*
     * 비밀번호 암호화를 위한 PasswordEncoder Bean 등록
     * BCryptPasswordEncoder를 사용하여 비밀번호를 암호화
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /*
     * SecurityFilterChain을 설정하는 메서드
     * HttpSecurity를 사용하여 보안 설정을 구성
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 인증이 필요 없는 요청 경로 설정
                        .requestMatchers(
                                HttpMethod.GET,
                                // Swagger
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/api/auth/login", // 로그인
                                "/actuator/health", // 헬스체크
                                "/api/customer/code",
                                "/api/file/code",
                                "/api/customer/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                .addFilterBefore(new JwtAuthFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
