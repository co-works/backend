package com.hanjeonerp.backend.module.engineer.infra;

import com.hanjeonerp.backend.module.engineer.domain.entity.Engineer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEngineerRepo extends JpaRepository<Engineer, Long> {

}
