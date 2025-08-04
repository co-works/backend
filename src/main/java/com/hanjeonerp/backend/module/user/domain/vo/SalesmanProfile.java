package com.hanjeonerp.backend.module.user.domain.vo;

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
    //수수료 정보
    private Double commissionRate; // 수수료율

    @Enumerated(EnumType.STRING)
    private SettlementMethod settlementMethod; // 정산 방법
    private String bankName; // 은행명
    private String bankAccount; // 계좌번호


    //사업자 정보
    private String businessNumber; // 사업자등록번호
    private String representative; // 대표자
    private String businessItem; // 업종
    private String businessType; // 업태
    private String businessAddress; // 사업장 주소



    }

