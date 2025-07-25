package com.hanjeonerp.backend.module.user.api.dto;

import com.hanjeonerp.backend.module.user.domain.vo.SalesmanProfile;
import com.hanjeonerp.backend.module.user.domain.vo.SettlementMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor //테스트용
@Builder    //테스트
public class SignUpReq {
    // 사용자 계정 정보
    private String username;
    private String password;

    // 영업사원 기본 정보
    private String salesmanName;
    private String salesmanPhone;
    private String salesmanEmail;
    private String salesmanAddress;

    // 수수료 및 정산 정보
    private Double commissionRate;
    private SettlementMethod settlementMethod; // "INVOICE", "WITHHOLDING_TAX"
    private String bankName;
    private String bankAccount;

    // 사업자 정보
    private String businessNumber;
    private String representative;
    private String businessItem;
    private String businessType;
    private String businessAddress;

    public static SalesmanProfile of(SignUpReq req) {
        return new SalesmanProfile(
                req.getSalesmanName(),
                req.getSalesmanPhone(),
                req.getSalesmanEmail(),
                req.getSalesmanAddress(),
                req.getCommissionRate(),
                req.getSettlementMethod(),
                req.getBankName(),
                req.getBankAccount(),
                req.getBusinessNumber(),
                req.getRepresentative(),
                req.getBusinessItem(),
                req.getBusinessType(),
                req.getBusinessAddress()
        );
    }
}
