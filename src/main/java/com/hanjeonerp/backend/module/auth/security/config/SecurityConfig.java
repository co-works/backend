package com.hanjeonerp.backend.module.auth.security.config;

import com.hanjeonerp.backend.module.auth.jwt.JwtAuthFilter;
import com.hanjeonerp.backend.module.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
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

    // Allow Origin List
    private static final List<String> ALLOWED_ORIGINS = List.of(
            "http://localhost:3000",
            "http://127.0.0.1:3000",
            "http://localhost:5173",
            "https://kepco-management.vercel.app",
            "https://v0-kepco-landing-page-git-main-kk3junes-projects.vercel.app/"

    );

    /*
     * SecurityFilterChain을 설정하는 메서드
     * HttpSecurity를 사용하여 보안 설정을 구성
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 인증이 필요 없는 요청 경로 설정
                        .requestMatchers(HttpMethod.GET,
                                // Swagger
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/actuator/health", // 헬스체크
                                "/api/file/code", // 파일 코드
                                "/api/customer/check/company-name", // 중복 수용가 체크
                                "/api/customer/code" // 수용가 코드
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/api/file/upload", // 파일 업로드
                                "/api/auth/login", // 로그인
                                "/api/file/view", // 파일 조회 url 생성
                                "/api/customer",// 수용가 생성
                                "/api/admin/register" //관리자 등록
                        ).permitAll()
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/file" // 파일 삭제
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                .addFilterBefore(new JwtAuthFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        ALLOWED_ORIGINS.forEach(config::addAllowedOriginPattern);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true); // JWT나 세션 쿠키 포함 시 필요

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
