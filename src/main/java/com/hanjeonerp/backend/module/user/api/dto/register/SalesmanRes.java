package com.hanjeonerp.backend.module.user.api.dto.register;

import com.hanjeonerp.backend.module.user.api.dto.SalesmanDto;
import com.hanjeonerp.backend.module.user.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SalesmanRes {
    private String username ;
    private SalesmanDto profile;

    public static SalesmanRes from(User user) {
        return SalesmanRes.builder()
                .username(user.getUsername())
                .profile(SalesmanDto.from(user.getBasicProfile(), user.getSalesmanProfile()))
                .build();
    }
}
