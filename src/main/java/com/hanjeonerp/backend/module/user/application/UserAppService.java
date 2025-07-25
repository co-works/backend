package com.hanjeonerp.backend.module.user.application;

import com.hanjeonerp.backend.module.user.api.dto.SignUpReq;
import com.hanjeonerp.backend.module.user.api.dto.SignUpRes;
import com.hanjeonerp.backend.module.user.domain.entity.User;
import com.hanjeonerp.backend.module.user.domain.repo.UserRepo;
import com.hanjeonerp.backend.module.user.domain.service.UserService;
import com.hanjeonerp.backend.module.user.domain.vo.SalesmanProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAppService {
    private final UserService userService;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpRes signUp(SignUpReq req) {
        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(req.getPassword());

        // 영업사원 프로필 생성
        SalesmanProfile profile = SalesmanProfile.of(req);

        //영업사원 생성
        User user = userService.createSalesMan(req.getUsername(), encodedPassword, profile);

        // 영업사원 저장
        userRepo.save(user);

        // 응답 DTO 생성
        return SignUpRes.from(user);
    }

}

