package com.hanjeonerp.backend.module.user.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SettlementMethod {
    INVOICE("계산서"),
    WITHHOLDING_TAX("원천징수");

    private final String description;

}
