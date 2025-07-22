package com.hanjeonerp.backend.module.customer.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TenantElectricUsage {
    JANUARY_USAGE("1월 전기 사용량"),
    AUGUST_USAGE("8월 전기 사용량"),
    ;

    private final String description;

}
