package com.hanjeonerp.backend.module.user.api.dto.update;


import com.hanjeonerp.backend.module.user.domain.entity.User;
import com.hanjeonerp.backend.module.user.domain.vo.UserBasicProfile;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateEngineerRes {

    private String name;
    private String phone;
    private String email;
    private String address;
    private String password;

   public static UpdateEngineerRes from(User User, String decryptedPassword) {
       UserBasicProfile profile = User.getBasicProfile();
       return UpdateEngineerRes.builder()
               .name(profile.getName())
               .phone(profile.getPhone())
               .email(profile.getEmail())
               .address(profile.getAddress())
                .password(User.getPassword())
               .build();
   }
}
