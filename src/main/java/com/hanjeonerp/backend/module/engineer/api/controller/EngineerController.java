package com.hanjeonerp.backend.module.engineer.api.controller;

import com.hanjeonerp.backend.core.common.ApiResponse;
import com.hanjeonerp.backend.module.engineer.api.dto.register.EngineerReq;
import com.hanjeonerp.backend.module.engineer.api.dto.register.EngineerRes;
import com.hanjeonerp.backend.module.engineer.api.dto.update.UpdateEngineerRes;
import com.hanjeonerp.backend.module.engineer.application.EngineerAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/engineers")
@RequiredArgsConstructor
public class EngineerController {

    private final EngineerAppService engineerAppService;

    //엔지니어 생성
    @PostMapping("/register")
    public ApiResponse <EngineerRes> createEngineer(EngineerReq req) {
        return ApiResponse.success(engineerAppService.createEngineer(req));
    }

    //엔지니어 수정
    @PostMapping("/update/{userId}")
    public ApiResponse<UpdateEngineerRes> updateEngineer(
            @PathVariable Long userId,
            EngineerReq req
    ) {
        return ApiResponse.success(engineerAppService.updateEngineer(userId, req));
    }

    //영업사원 삭제
    @DeleteMapping("/delete/{userId}")
    public ApiResponse<Void> deleteEngineer(@PathVariable Long userId) {
        engineerAppService.deleteEngineer(userId);
        return ApiResponse.success();
    }
}
