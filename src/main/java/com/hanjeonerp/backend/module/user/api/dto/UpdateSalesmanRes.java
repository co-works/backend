package com.hanjeonerp.backend.module.user.api.dto;

import com.hanjeonerp.backend.module.user.domain.entity.User;
import com.hanjeonerp.backend.module.user.domain.vo.SalesmanProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
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

    // User 객체로부터 UpdateSalesmanRes 생성
    public static UpdateSalesmanRes from(User user) {
        SalesmanProfile profile = user.getSalesmanProfile();
        return UpdateSalesmanRes.of(
                user.getUsername(),
                profile.getSalesmanName(),
                profile.getSalesmanPhone(),
                profile.getSalesmanEmail(),
                profile.getSalesmanAddress(),
                profile.getCommissionRate(),
                profile.getSettlementMethod().name(),
                profile.getBankName(),
                profile.getBankAccount(),
                profile.getBusinessNumber(),
                profile.getRepresentative(),
                profile.getBusinessItem(),
                profile.getBusinessType(),
                profile.getBusinessAddress()
        );
    }
}
