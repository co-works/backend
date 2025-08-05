package com.hanjeonerp.backend.module.user.infra.repo;

import com.hanjeonerp.backend.module.user.domain.entity.User;
import com.hanjeonerp.backend.module.user.domain.vo.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaUserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String value);
    boolean existsByUsername(String value);

    Long countByRoleAndIsDeleted(Role role, boolean deleted);

    List<User> findByRoleAndIsDeleted(Role role, boolean deleted);
}
