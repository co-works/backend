package com.hanjeonerp.backend.module.user.api.dto.register;

import com.hanjeonerp.backend.module.user.domain.entity.User;
import com.hanjeonerp.backend.module.user.domain.vo.UserBasicProfile;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EngineerRes {
    private UserBasicProfile engineerProfile;
    private String password;


    // 엔지니어 응답 DTO 생성
    public static EngineerRes from(User user, String decryptedPassword) {
        return EngineerRes.builder()
                .engineerProfile(user.getBasicProfile())
                .password(user.getPassword())
                .build();

    }
}
