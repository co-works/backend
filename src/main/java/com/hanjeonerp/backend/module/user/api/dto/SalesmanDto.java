package com.hanjeonerp.backend.module.user.api.dto;

import com.hanjeonerp.backend.module.user.domain.vo.SalesmanProfile;
import com.hanjeonerp.backend.module.user.domain.vo.UserBasicProfile;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SalesmanDto {
    // 기본 정보
    private String name;
    private String phone;
    private String email;
    private String address;

    // 수수료 정보
    private Double commissionRate;
    private String settlementMethod; // Enum → 문자열 변환

    private String bankName;
    private String bankAccount;

    // 사업자 정보
    private String businessNumber;
    private String representative;
    private String businessItem;
    private String businessType;
    private String businessAddress;

    public static SalesmanDto from(UserBasicProfile userBasicProfile, SalesmanProfile profile) {
        return SalesmanDto.builder()
                .name(userBasicProfile.getName())
                .phone(userBasicProfile.getPhone())
                .email(userBasicProfile.getEmail())
                .address(userBasicProfile.getAddress())
                .commissionRate(profile.getCommissionRate())
                .settlementMethod(profile.getSettlementMethod().name())
                .bankName(profile.getBankName())
                .bankAccount(profile.getBankAccount())
                .businessNumber(profile.getBusinessNumber())
                .representative(profile.getRepresentative())
                .businessItem(profile.getBusinessItem())
                .businessType(profile.getBusinessType())
                .businessAddress(profile.getBusinessAddress())
                .build();
    }
}
