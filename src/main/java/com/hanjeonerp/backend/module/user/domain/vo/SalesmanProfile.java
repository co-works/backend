package com.hanjeonerp.backend.module.user.domain.vo;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

// 영업사원 프로필을 나타내는 Value Object 클래스
@Embeddable
@Getter
public class SalesmanProfile {

    private Double commissionRate; // 수수료율
    private String bankName; // 은행명
    private String bankAccount; // 계좌번호
    private String address; // 주소
    private String businessName; // 사업자명
    private String businessNumber; // 사업자등록번호
    private String businessItem; // 업종
    private String businessType; // 업태
    private String representative; // 대표자
    private String businessAddress; // 사업장 주소

    @Enumerated(EnumType.STRING)
    private SettlementMethod settlementMethod; // 정산 방법

}

