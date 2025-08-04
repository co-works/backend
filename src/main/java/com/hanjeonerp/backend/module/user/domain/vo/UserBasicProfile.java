package com.hanjeonerp.backend.module.user.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserBasicProfile {
    private String name;
    private String phone;
    private String email;
    private String address;

    public static UserBasicProfile of(String name, String phone, String email, String address) {
        return new UserBasicProfile(name, phone, email, address);
    }
}
