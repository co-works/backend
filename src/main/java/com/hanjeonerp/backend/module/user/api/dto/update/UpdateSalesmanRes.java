package com.hanjeonerp.backend.module.user.api.dto.update;

import com.hanjeonerp.backend.module.user.domain.entity.User;
import com.hanjeonerp.backend.module.user.domain.vo.SalesmanProfile;
import com.hanjeonerp.backend.module.user.domain.vo.UserBasicProfile;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateSalesmanRes {

    // 계정 정보
    private String username;

    // 영업사원 기본 정보
    private String salesmanName;
    private String salesmanPhone;
    private String salesmanEmail;
    private String salesmanAddress;

    // 수수료 및 정산 정보
    private Double commissionRate;
    private String settlementMethod;
    private String bankName;
    private String bankAccount;

    // 사업자 정보
    private String businessNumber;
    private String representative;
    private String businessItem;
    private String businessType;
    private String businessAddress;
    private String password;

    // User 객체로부터 UpdateSalesmanRes 생성
    public static UpdateSalesmanRes from(User user, String decryptedPassword) {
        UserBasicProfile basicProfile = user.getBasicProfile();
        SalesmanProfile profile = user.getSalesmanProfile();
        return UpdateSalesmanRes.builder()
                .username(user.getUsername())
                .salesmanName(basicProfile.getName())
                .salesmanPhone(basicProfile.getPhone())
                .salesmanEmail(basicProfile.getEmail())
                .salesmanAddress(basicProfile.getAddress())
                .commissionRate(profile.getCommissionRate())
                .settlementMethod(profile.getSettlementMethod().name())
                .bankName(profile.getBankName())
                .bankAccount(profile.getBankAccount())
                .businessNumber(profile.getBusinessNumber())
                .representative(profile.getRepresentative())
                .businessItem(profile.getBusinessItem())
                .businessType(profile.getBusinessType())
                .businessAddress(profile.getBusinessAddress())
                .password(decryptedPassword) // 비밀번호는 복호화된 값으로 설정
                .build(
        );
    }
}

