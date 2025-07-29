package com.hanjeonerp.backend.module.engineer.domain.service;

import com.hanjeonerp.backend.module.engineer.domain.entity.Engineer;
import com.hanjeonerp.backend.module.engineer.domain.vo.EngineerProfile;
import org.springframework.stereotype.Component;

@Component
public class EngineerService {

    // 엔지니어 생성 로직
    public Engineer createEngineer(EngineerProfile engineerProfile) {
        return Engineer.createEngineer(engineerProfile);
    }
}
