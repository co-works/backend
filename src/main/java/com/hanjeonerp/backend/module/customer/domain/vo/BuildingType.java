package com.hanjeonerp.backend.module.customer.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BuildingType {
    //건축물 형태(공장, 지식산업센터, 빌딩, 주상복합, 아파트단지(공동주택), 학교, 호텔, 기타)
    FACTORY("공장"),
    KNOWLEDGE_INDUSTRY_CENTER("지식산업센터"),
    BUILDING("빌딩"),
    MIXED_USE_COMPLEX("주상복합"),
    APARTMENT_COMPLEX("아파트단지(공동주택)"),
    SCHOOL("학교"),
    HOTEL("호텔"),
    OTHER("기타");

    private final String label;

}
