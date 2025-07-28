package com.hanjeonerp.backend.module.engineer.infra;


import com.hanjeonerp.backend.core.exception.BadRequestException;
import com.hanjeonerp.backend.module.engineer.domain.entity.Engineer;
import com.hanjeonerp.backend.module.engineer.domain.repo.EngineerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EngineerRepoImpl implements EngineerRepo {

    private final JpaEngineerRepo jpaEngineerRepo;

    @Override
    public Engineer save(Engineer engineer) {
        return  jpaEngineerRepo.save(engineer);
    }

    @Override
    public Optional<Engineer> findById(Long id) {
        return  jpaEngineerRepo.findById(id);
    }

}
