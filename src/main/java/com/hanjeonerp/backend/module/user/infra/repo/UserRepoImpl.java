package com.hanjeonerp.backend.module.user.infra.repo;

import com.hanjeonerp.backend.core.exception.BadRequestException;
import com.hanjeonerp.backend.module.user.domain.entity.User;
import com.hanjeonerp.backend.module.user.domain.repo.UserRepo;
import com.hanjeonerp.backend.module.user.domain.vo.Username;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepoImpl implements UserRepo {

    private final JpaUserRepo jpaUserRepo;

    @Override
    public User save(User user) {
        return jpaUserRepo.save(user);
    }

    @Override
    public Optional<User> findByUsername(Username username) {
        return  jpaUserRepo.findByUsername(username.getValue());
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaUserRepo.existsByUsername(username);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepo.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        User user = jpaUserRepo.findById(id)
                .orElseThrow(() -> new BadRequestException("유저가 존재하지 않습니다."));
        user.delete();               //soft delete
        jpaUserRepo.save(user);
    }


}
