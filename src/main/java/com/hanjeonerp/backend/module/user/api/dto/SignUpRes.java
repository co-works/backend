package com.hanjeonerp.backend.module.user.api.dto;

import com.hanjeonerp.backend.module.user.domain.entity.User;
import com.hanjeonerp.backend.module.user.domain.vo.Username;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpRes {
    private String username ;
    private SalesmanDto profile;

    public static SignUpRes from(User user) {
        return SignUpRes.builder()
                .username(user.getUsername())
                .profile(SalesmanDto.from(user.getSalesmanProfile()))
                .build();
    }
}
