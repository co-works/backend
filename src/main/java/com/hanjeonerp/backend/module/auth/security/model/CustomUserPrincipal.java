package com.hanjeonerp.backend.module.auth.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * CustomUserPrincipal 클래스는 Spring Security의 UserDetails 인터페이스를 구현하여
 * 사용자 정보를 담는 역할
 * 사용자 ID와 이름을 포함하며, 인증 관련 메서드를 구현
 */
@Getter
@AllArgsConstructor
public class CustomUserPrincipal implements UserDetails {

    private final Long userId;
    private final String username;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return null; // 로그인 후엔 필요 없음
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
