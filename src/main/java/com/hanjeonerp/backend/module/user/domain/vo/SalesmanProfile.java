package com.hanjeonerp.backend.module.user.domain.vo;

import com.hanjeonerp.backend.module.user.api.dto.SignUpReq;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 영업사원 프로필을 나타내는 Value Object 클래스
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Getter
public class SalesmanProfile {
    //영업사원 기본 정보
    private String salesmanName; // 영업사원 이름
    private String salesmanPhone; // 영업사원 전화번호
    private String salesmanEmail; // 영업사원 이메일
    private String salesmanAddress; // 영업사원 주소

    //수수료 정보
    private Double commissionRate; // 수수료율
    @Enumerated(EnumType.STRING)
    private SettlementMethod settlementMethod; // 정산 방법
    private String bankName; // 은행명
    private String bankAccount; // 계좌번호

    private String businessNumber; // 사업자등록번호
    private String representative; // 대표자
    private String businessItem; // 업종
    private String businessType; // 업태
    private String businessAddress; // 사업장 주소


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

