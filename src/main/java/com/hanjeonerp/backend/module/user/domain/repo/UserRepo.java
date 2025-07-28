package com.hanjeonerp.backend.module.user.domain.repo;

import com.hanjeonerp.backend.module.user.domain.entity.User;
import com.hanjeonerp.backend.module.user.domain.vo.Username;

import java.util.Optional;

public interface UserRepo {
    // 사용자 저장
    User save(User user);
    // 사용자 조회
    Optional<User> findByUsername(Username username);
    // 사용자 아이디 중복 체크
    boolean existsByUsername(String username);
    // 사용자 ID로 조회
    Optional<User> findById(Long id);
    // 사용자 ID로 삭제
    void deleteById(Long id);
}
