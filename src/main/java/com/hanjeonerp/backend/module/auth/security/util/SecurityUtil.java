package com.hanjeonerp.backend.module.auth.security.util;


import com.hanjeonerp.backend.core.exception.BadRequestException;
import com.hanjeonerp.backend.core.exception.ErrorCode;
import com.hanjeonerp.backend.module.auth.security.model.CustomUserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Security 관련 유틸리티 클래스
 * 현재 인증된 사용자의 ID와 이름을 가져오는 메서드를 제공
 */
public class SecurityUtil {

    // 생성자를 private으로 설정하여 인스턴스 생성을 방지
    private SecurityUtil() {}


    // 현재 인증된 사용자의 ID를 반환
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 없거나 인증되지 않은 경우 예외를 발생
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BadRequestException(ErrorCode.UNAUTHORIZED, "인증되지 않은 사용자입니다.");
        }

        Object principal = authentication.getPrincipal();

        // principal이 CustomUserPrincipal 타입이 아닌 경우 예외를 발생
        if (!(principal instanceof CustomUserPrincipal)) {
            throw new BadRequestException(ErrorCode.UNAUTHORIZED, "인증되지 않은 사용자입니다.");
        }

        return ((CustomUserPrincipal) principal).getUserId();
    }

    // 현재 인증된 사용자의 이름을 반환
    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
