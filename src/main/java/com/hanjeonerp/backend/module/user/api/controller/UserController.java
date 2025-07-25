package com.hanjeonerp.backend.module.user.api.controller;

import com.hanjeonerp.backend.core.common.ApiResponse;
import com.hanjeonerp.backend.module.user.api.dto.SignUpReq;
import com.hanjeonerp.backend.module.user.api.dto.SignUpRes;
import com.hanjeonerp.backend.module.user.application.UserAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserAppService userAppService;

    @PostMapping("/users/signup")
    public ApiResponse<SignUpRes> createsalesman(@RequestBody SignUpReq signUpReq) {
        return ApiResponse.success(userAppService.signUp(signUpReq));
    }

}
