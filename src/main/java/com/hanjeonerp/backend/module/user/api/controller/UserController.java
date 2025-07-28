package com.hanjeonerp.backend.module.user.api.controller;

import com.hanjeonerp.backend.core.common.ApiResponse;
import com.hanjeonerp.backend.module.user.api.dto.SignUpReq;
import com.hanjeonerp.backend.module.user.api.dto.SignUpRes;
import com.hanjeonerp.backend.module.user.api.dto.UpdateSalesmanReq;
import com.hanjeonerp.backend.module.user.api.dto.UpdateSalesmanRes;
import com.hanjeonerp.backend.module.user.application.UserAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserAppService userAppService;

    // 영업사원 회원가입
    @PostMapping("/salesman/signup")
    public ApiResponse<SignUpRes> createsalesman(@RequestBody SignUpReq signUpReq) {
        return ApiResponse.success(userAppService.signUp(signUpReq));
    }

    // 영업사원 수정
    @PutMapping("/salesman/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<UpdateSalesmanRes> updateSalesman(
            @PathVariable Long userId,
            @RequestBody UpdateSalesmanReq req
    ) {
        return ApiResponse.success(userAppService.updateSalesman(userId, req));
    }

    // 영업사원 삭제
    @DeleteMapping("/salesman/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteSalesman(@PathVariable Long userId) {
        userAppService.deleteSalesman(userId);
        return ApiResponse.success();
    }

}
