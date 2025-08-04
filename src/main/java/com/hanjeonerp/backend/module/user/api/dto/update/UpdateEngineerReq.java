package com.hanjeonerp.backend.module.user.api.dto.update;

import com.hanjeonerp.backend.module.user.domain.vo.UserBasicProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateEngineerReq {

    //엔지니어 정보
    private String name;
    private String phone;
    private String email;
    private String address;

    public UserBasicProfile toprofile(UserBasicProfile existingProfile) {
        String name = existingProfile.getName();
        if (this.name !=null) name = this.name;

        String phone = existingProfile.getPhone();
        if (this.phone != null) phone = this.phone;

        String email = existingProfile.getEmail();
        if (this.email != null) email = this.email;

        String address = existingProfile.getAddress();
        if (this.address != null) address = this.address;

        return new UserBasicProfile(
                name,
                phone,
                email,
                address
        );
    }
}
