package com.hanjeonerp.backend.module.auth.api.controller;


import com.hanjeonerp.backend.core.common.ApiResponse;
import com.hanjeonerp.backend.module.auth.api.dto.LoginReq;
import com.hanjeonerp.backend.module.auth.api.dto.LoginRes;
import com.hanjeonerp.backend.module.auth.application.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ApiResponse<LoginRes> login(@RequestBody LoginReq loginReq) {
        return ApiResponse.success( loginService.login(loginReq)
        );
    }
}
