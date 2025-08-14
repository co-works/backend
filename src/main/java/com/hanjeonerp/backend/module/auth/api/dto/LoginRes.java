package com.hanjeonerp.backend.module.auth.api.dto;

import com.hanjeonerp.backend.module.user.domain.entity.User;
import com.hanjeonerp.backend.module.user.domain.vo.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRes {
    private String username;
    private String accessToken;
    private Role role;

    public static LoginRes from(User user, String accessToken) {
        return new LoginRes(
                user.getUsername(),
                accessToken,
                user.getRole()
        );
    }

}
