package com.hanjeonerp.backend.module.file.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileCategory {

    BUSINESS_LICENSE("사업자등록증"),          //  등록 시 제출
    ELECTRICAL_DIAGRAM("변전실 도면"),         // 등록 시 제출 (최대 5개)
    GOMETA_EXCEL("입주사별 전력사용량 자료"),     // 조건부 필수 - 등록 시 제출

    INSPECTION_REPORT("실사 검토 보고서"),
    CONTRACT("계약서"),
    SAVINGS_PROOF("전기요금 절감 확인서"),
    INSURANCE("보증 보험 증권"),
    KEPCO_APPLICATION("한전 대관 신청서");

    private final String description; // 파일 카테고리 설명

}
