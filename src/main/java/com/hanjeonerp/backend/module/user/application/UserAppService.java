package com.hanjeonerp.backend.module.user.application;

import com.hanjeonerp.backend.core.exception.BadRequestException;
import com.hanjeonerp.backend.core.util.CryptoUtil;
import com.hanjeonerp.backend.module.user.api.dto.register.*;
import com.hanjeonerp.backend.module.user.api.dto.update.UpdateEngineerRes;
import com.hanjeonerp.backend.module.user.api.dto.update.UpdateSalesmanReq;
import com.hanjeonerp.backend.module.user.api.dto.update.UpdateSalesmanRes;
import com.hanjeonerp.backend.module.user.domain.entity.User;
import com.hanjeonerp.backend.module.user.domain.repo.UserRepo;
import com.hanjeonerp.backend.module.user.domain.service.UserService;
import com.hanjeonerp.backend.module.user.domain.vo.SalesmanProfile;
import com.hanjeonerp.backend.module.user.domain.vo.UserBasicProfile;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAppService {
    private final UserService userService;
    private final UserRepo userRepo;
    private final CryptoUtil cryptoUtil;

    // 관리자 등록
    @Transactional
    public AdminRes signUpAdmin(@Valid AdminReq req) {
        String rawPassword = req.getPassword();
        String encrypted = cryptoUtil.encrypt(rawPassword);

        UserBasicProfile basicProfile = req.toProfile();
        User user = userService.createAdmin(req.getUsername(), encrypted, basicProfile);

        userRepo.save(user);

        // 복호화해서 평문 전달
        return AdminRes.from(user, cryptoUtil.decrypt(user.getPassword()));
    }

    // 영업사원 등록
    @Transactional
    public SalesmanRes signUp(SalesmanReq req) {
        String rawPassword = req.getPassword();
        String encrypted = cryptoUtil.encrypt(rawPassword);

        UserBasicProfile basicProfile = req.toBasicProfile();
        SalesmanProfile profile = req.toSalesmanProfile();

        User user = userService.createSalesman(req.getUsername(), encrypted, basicProfile, profile);
        userRepo.save(user);

        return SalesmanRes.from(user, cryptoUtil.decrypt(user.getPassword()));
    }

    // 영업사원 수정
    @Transactional
    public UpdateSalesmanRes updateSalesman(Long userId, UpdateSalesmanReq req) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new BadRequestException("수정할 유저가 존재하지 않습니다."));

        if (req.getUsername() != null && !req.getUsername().equals(user.getUsername())) {
            if (userRepo.existsByUsername(req.getUsername())) {
                throw new BadRequestException("이미 사용 중인 아이디입니다.");
            }
            user.changeUsername(req.getUsername());
        }

        if (req.getPassword() != null) {
            String encrypted = cryptoUtil.encrypt(req.getPassword());
            user.changePassword(encrypted);
        }

        UserBasicProfile newBasic = req.toBasicProfile(user.getBasicProfile());
        SalesmanProfile newSalesman = req.toSalesmanProfile(user.getSalesmanProfile());
        user.changeSalesmaneProfile(newBasic, newSalesman);

        return UpdateSalesmanRes.from(user, cryptoUtil.decrypt(user.getPassword()));
    }

    // 영업사원 삭제
    @Transactional
    public void deleteSalesman(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new BadRequestException("삭제할 유저가 존재하지 않습니다."));
        user.delete();
        userRepo.save(user);
    }

    //  엔지니어 등록
    @Transactional
    public EngineerRes createEngineer(EngineerReq req) {
        String encrypted = cryptoUtil.encrypt(req.getPassword());

        UserBasicProfile engineerProfile = req.toProfile();
        User engineer = userService.createEngineer(req.getUsername(), encrypted, engineerProfile);

        engineer = userRepo.save(engineer);

        return EngineerRes.from(engineer, cryptoUtil.decrypt(engineer.getPassword()));
    }

    // 엔지니어 수정
    @Transactional
    public UpdateEngineerRes updateEngineer(Long userId, EngineerReq req) {
        User engineer = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("수정할 엔지니어가 존재하지 않습니다."));

        if (req.getPassword() != null) {
            String encrypted = cryptoUtil.encrypt(req.getPassword());
            engineer.changePassword(encrypted);
        }

        UserBasicProfile engineerProfile = req.toProfile();
        engineer.changeEngineerProfile(engineerProfile);

        engineer = userRepo.save(engineer);
        return UpdateEngineerRes.from(engineer, cryptoUtil.decrypt(engineer.getPassword()));
    }

    // 엔지니어 삭제
    @Transactional
    public void deleteEngineer(Long userId) {
        User engineer = userRepo.findById(userId)
                .orElseThrow(() -> new BadRequestException("삭제할 엔지니어가 존재하지 않습니다."));
        engineer.delete();
        userRepo.save(engineer);
    }


}
