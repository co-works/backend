package com.hanjeonerp.backend.module.customer.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProgressStatus {

//타당성 검토 의뢰, 실사, 실사 보고서, 계약, 시공, 사업확인서, 수수료 정산, 반려, 비고
//    REQUEST_FOR_FEASIBILITY_REVIEW("타당성 검토 의뢰"),
//    SURVEY("실사"),
//    SURVEY_REPORT("실사 보고서"),
//    CONTRACT("계약"),
//    CONSTRUCTION("시공"),
//    BUSINESS_CONFIRMATION("사업확인서"),
//    FEE_SETTLEMENT("수수료 정산"),
//    REJECTED("반려"),
//    NOTE("비고"),
    // 변경: 의뢰, 진행중, 완료, 반려
    REQUESTED("의뢰"),
    IN_PROGRESS("진행중"),
    COMPLETE("완료"),
    REJECTED("반려");

    private final String label;

}
