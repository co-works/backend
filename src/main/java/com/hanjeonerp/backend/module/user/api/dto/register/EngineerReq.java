package com.hanjeonerp.backend.module.user.api.dto.register;

import com.hanjeonerp.backend.module.user.domain.vo.UserBasicProfile;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EngineerReq {

    // 엔지니어 정보
    private String username;
    private String password;
    @NotNull
    private String name;
    @NotNull
    private String phone;
    @NotNull
    private String email;
    @NotNull
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
