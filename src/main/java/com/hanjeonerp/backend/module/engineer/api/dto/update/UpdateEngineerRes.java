package com.hanjeonerp.backend.module.engineer.api.dto.update;

import com.hanjeonerp.backend.module.engineer.domain.entity.Engineer;
import com.hanjeonerp.backend.module.engineer.domain.vo.EngineerProfile;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateEngineerRes {

    private String name;
    private String phone;
    private String email;
    private String address;

   public static UpdateEngineerRes from(Engineer engineer) {
       EngineerProfile profile = engineer.getEngineerProfile();
       return UpdateEngineerRes.builder()
               .name(profile.getName())
               .phone(profile.getPhone())
               .email(profile.getEmail())
               .address(profile.getAddress())
               .build();
   }
}
