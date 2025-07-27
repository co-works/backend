package com.hanjeonerp.backend.module.user.application;

import com.hanjeonerp.backend.core.exception.BadRequestException;
import com.hanjeonerp.backend.module.user.api.dto.SignUpReq;
import com.hanjeonerp.backend.module.user.api.dto.SignUpRes;
import com.hanjeonerp.backend.module.user.api.dto.UpdateSalesmanReq;
import com.hanjeonerp.backend.module.user.api.dto.UpdateSalesmanRes;
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

    // 영업사원 회원가입
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

    @Transactional
    public UpdateSalesmanRes updateSalesman(Long userId, UpdateSalesmanReq req) {
        // 수정 대상은 userId로 찾음!
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new BadRequestException("수정할 유저가 존재하지 않습니다."));

        // username 변경
        if (req.getUsername() != null && !req.getUsername().equals(user.getUsername())) {
            if (userRepo.existsByUsername(req.getUsername())) {
                throw new BadRequestException("이미 사용 중인 아이디입니다.");
            }
            user.changeUsername(req.getUsername());
        }

        // password 변경
        if (req.getPassword() != null) {
            user.changePassword(passwordEncoder.encode(req.getPassword()));
        }

        // profile 변경
        SalesmanProfile updatedProfile = req.toProfile(user.getSalesmanProfile());
        user.changeProfile(updatedProfile);

        return UpdateSalesmanRes.from(user);
    }

    // 영업사원 삭제
    @Transactional
    public void deleteSalesman(Long userId) {
        // 삭제 대상은 userId로 찾음
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new BadRequestException("삭제할 유저가 존재하지 않습니다."));

        // 소프트 삭제
        user.delete();
        userRepo.save(user);
    }

}



