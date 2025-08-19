package com.hanjeonerp.backend.module.user.api.controller;

import com.hanjeonerp.backend.core.common.ApiResponse;
import com.hanjeonerp.backend.module.user.api.dto.register.*;
import com.hanjeonerp.backend.module.user.api.dto.update.UpdateEngineerRes;
import com.hanjeonerp.backend.module.user.api.dto.update.UpdateSalesmanReq;
import com.hanjeonerp.backend.module.user.api.dto.update.UpdateSalesmanRes;
import com.hanjeonerp.backend.module.user.application.UserAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "User", description = "사용자 관련 API")
public class UserController {

    private final UserAppService userAppService;

    // 관리자 등록
    @Operation(summary = "관리자 등록")
    @PostMapping("/admin/register")
    public ApiResponse<AdminRes> createAdmin(@Valid @RequestBody AdminReq adminReq) {
        return ApiResponse.success(userAppService.signUpAdmin(adminReq));
    }

    // 영업사원 등록
    @Operation(summary = "영업사원 등록")
    @PostMapping("/salesman/register")
    public ApiResponse<SalesmanRes> createSalesman(@Valid @RequestBody SalesmanReq salesmanReq) {
        return ApiResponse.success(userAppService.signUp(salesmanReq));
    }

    // 영업사원 수정
    @Operation(summary = "영업사원 수정")
    @PutMapping("/salesman/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<UpdateSalesmanRes> updateSalesman(
            @Valid
            @PathVariable Long userId,
            @RequestBody UpdateSalesmanReq req
    ) {
        return ApiResponse.success(userAppService.updateSalesman(userId, req));
    }

    // 영업사원 삭제
    @Operation(summary = "영업사원 삭제")
    @DeleteMapping("/salesman/{userId}")
    public ApiResponse<Void> deleteSalesman(@PathVariable Long userId) {
        userAppService.deleteSalesman(userId);
        return ApiResponse.success();
    }

    //기술사 등록
    @Operation(summary = "기술사 등록")
    @PostMapping("/engineer/register")
    public ApiResponse<EngineerRes> createEngineer(@Valid @RequestBody EngineerReq engineerReq) {
        return ApiResponse.success(userAppService.createEngineer(engineerReq));
    }

    // 기술사 수정
    @Operation(summary = "기술사 수정")
    @PutMapping("/engineer/{userId}")
    public ApiResponse<UpdateEngineerRes> updateEngineer(
            @Valid
            @PathVariable Long userId,
            @RequestBody EngineerReq req
    ) {
        return ApiResponse.success(userAppService.updateEngineer(userId, req));
    }

    // 기술사 삭제
    @Operation(summary = "기술사 삭제")
    @DeleteMapping("/engineer/{userId}")
    public ApiResponse<Void> deleteEngineer(@PathVariable Long userId) {
        userAppService.deleteEngineer(userId);
        return ApiResponse.success();
    }
}
