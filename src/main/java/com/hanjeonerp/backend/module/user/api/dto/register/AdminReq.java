package com.hanjeonerp.backend.module.user.api.dto.register;

import com.hanjeonerp.backend.module.user.domain.vo.UserBasicProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminReq {
    // 관리자 정보
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String address;

    public UserBasicProfile toProfile() {
        return UserBasicProfile.of(
                this.name,
                this.phone,
                this.email,
                this.address
        );
    }
}
