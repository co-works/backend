package com.hanjeonerp.backend.module.engineer.api.dto.register;

import com.hanjeonerp.backend.module.engineer.domain.entity.Engineer;
import com.hanjeonerp.backend.module.engineer.domain.vo.EngineerProfile;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EngineerRes {
    private EngineerProfile engineerProfile;


    // 엔지니어 응답 DTO 생성
    public static EngineerRes from(Engineer engineer) {
        return EngineerRes.builder()
                .engineerProfile(engineer.getEngineerProfile())
                .build();

    }
}
