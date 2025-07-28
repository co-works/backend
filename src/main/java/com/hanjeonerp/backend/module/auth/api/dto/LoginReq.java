package com.hanjeonerp.backend.module.auth.api.dto;

import lombok.Getter;

@Getter
public class LoginReq {

    private String username;
    private String password;
}
