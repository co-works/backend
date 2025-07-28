package com.hanjeonerp.backend.module.engineer.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class EngineerProfile {

    private String name;
    private String phone;
    private String email;
    private String address;

    public EngineerProfile(String name, String phone, String email, String address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public static EngineerProfile of(String name, String phone, String email, String address) {
        return new EngineerProfile(name, phone, email, address);
    }
}
