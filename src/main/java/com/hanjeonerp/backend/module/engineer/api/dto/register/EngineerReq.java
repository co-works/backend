package com.hanjeonerp.backend.module.engineer.api.dto.register;

import com.hanjeonerp.backend.module.engineer.domain.vo.EngineerProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EngineerReq {

    private String name;
    private String phone;
    private String email;
    private String address;

    public EngineerProfile toProfile() {
        return EngineerProfile.of(name, phone, email, address);
    }
}
