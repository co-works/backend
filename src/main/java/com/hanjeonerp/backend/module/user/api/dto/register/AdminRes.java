package com.hanjeonerp.backend.module.user.api.dto.register;

import com.hanjeonerp.backend.module.user.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminRes {
    private String username;
    private String name;
    private String phone;
    private String email;

    // 관리자 응답 DTO 생성
    public static AdminRes from(User user) {
        return AdminRes.builder()
                .username(user.getUsername())
                .name(user.getBasicProfile().getName())
                .phone(user.getBasicProfile().getPhone())
                .email(user.getBasicProfile().getEmail())
                .build();
    }
}
