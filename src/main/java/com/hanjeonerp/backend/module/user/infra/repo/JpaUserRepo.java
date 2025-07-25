package com.hanjeonerp.backend.module.user.infra.repo;

import com.hanjeonerp.backend.module.user.domain.entity.User;
import com.hanjeonerp.backend.module.user.domain.vo.Username;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String value);
    boolean existsByUsername(String value);
}
