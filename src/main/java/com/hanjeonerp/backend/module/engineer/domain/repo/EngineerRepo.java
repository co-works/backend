package com.hanjeonerp.backend.module.engineer.domain.repo;

import com.hanjeonerp.backend.module.engineer.domain.entity.Engineer;

import java.util.Optional;

public interface EngineerRepo {
    //저장
    Engineer save(Engineer engineer);

    //엔지니어 전체 조회
    Optional<Engineer> findById(Long id);

}
