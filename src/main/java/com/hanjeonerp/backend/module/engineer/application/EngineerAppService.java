package com.hanjeonerp.backend.module.engineer.application;

import com.hanjeonerp.backend.core.exception.BadRequestException;
import com.hanjeonerp.backend.module.engineer.api.dto.register.EngineerReq;
import com.hanjeonerp.backend.module.engineer.api.dto.register.EngineerRes;
import com.hanjeonerp.backend.module.engineer.api.dto.update.UpdateEngineerRes;
import com.hanjeonerp.backend.module.engineer.domain.entity.Engineer;
import com.hanjeonerp.backend.module.engineer.domain.repo.EngineerRepo;
import com.hanjeonerp.backend.module.engineer.domain.service.EngineerService;
import com.hanjeonerp.backend.module.engineer.domain.vo.EngineerProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EngineerAppService {

    private final EngineerService engineerService;
    private final EngineerRepo engineerRepo;

    //엔지니어 추가
    public EngineerRes createEngineer(EngineerReq req) {
        //엔지니어 프로필 생성
        EngineerProfile engineerProfile =  req.toProfile();

        //엔지니어 생성
        Engineer engineer = engineerService.createEngineer(engineerProfile);

        //엔지니어 저장
        engineer = engineerRepo.save(engineer);

        //엔지니어 응답 DTO 생성
        return EngineerRes.from(engineer);
    }

    //엔지니어 수정
    public UpdateEngineerRes updateEngineer(Long userId, EngineerReq req) {
        //수정할 대상 찾기
        Engineer engineer = engineerRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("수정할 엔지니어가 존재하지 않습니다."));

        //엔지니어 프로필 변경
        EngineerProfile engineerProfile = req.toProfile();
        engineer.changeProfile(engineerProfile);

        //엔지니어 저장
        engineer = engineerRepo.save(engineer);

        //엔지니어 응답 DTO 생성
        return UpdateEngineerRes.from(engineer);
    }

    //엔지니어 삭제 soft delete
    public void deleteEngineer(Long userId) {
        Engineer engineer = engineerRepo.findById(userId)
                .orElseThrow(() -> new BadRequestException("삭제할 엔지니어가 존재하지 않습니다."));

        // 엔지니어 삭제
        engineer.delete();
        engineerRepo.save(engineer);
    }
}
