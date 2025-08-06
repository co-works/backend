package com.hanjeonerp.backend.module.home.controller;

import com.hanjeonerp.backend.core.common.ApiResponse;
import com.hanjeonerp.backend.module.auth.jwt.JwtTokenProvider;
import com.hanjeonerp.backend.module.auth.security.model.CustomUserPrincipal;
import com.hanjeonerp.backend.module.home.dto.res.*;
import com.hanjeonerp.backend.module.home.service.HomeService;
import com.hanjeonerp.backend.module.user.domain.vo.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
@Tag(name = "Home", description = "메인페이지 관련 컨트롤러")
public class HomeController {

    private final HomeService homeService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/admin-dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "어드민 - 대시보드")
    public ApiResponse<AdminDashboardRes> adminDashboard(@AuthenticationPrincipal CustomUserPrincipal userPrincipal) {
        return ApiResponse.success(homeService.adminDashboard());
    }

    @GetMapping("/admin-salesman")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "어드민 - 영업자 관리")
    public ApiResponse<AdminSalesmanRes> adminSalesman() {
        return ApiResponse.success(homeService.adminSalesman());
    }

    @GetMapping("/admin-engineer")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "어드민 - 기술자 관리")
    public ApiResponse<AdminEngineerRes> adminEngineer() {
        return ApiResponse.success(homeService.adminEngineer());
    }

    @GetMapping("/admin-customer")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "어드민 - 수용가 관리")
    public ApiResponse<AdminCustomerRes> adminCustomer() {
        return ApiResponse.success(homeService.adminCustomer());
    }

    @GetMapping("/user-customer")
    @PreAuthorize("hasRole('SALESMAN') or hasRole('ENGINEER')")
    @Operation(summary = "유저 - 할당된 수용가 조회")
    public ApiResponse<UserCustomerRes> userCustomer(@AuthenticationPrincipal CustomUserPrincipal userPrincipal) {
        Long userId = userPrincipal.getUserId();
        Role role = userPrincipal.getRole();

        return ApiResponse.success(homeService.userCustomer(userId, role));
    }
}
