package com.hanjeonerp.backend.module.customer.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UpdateCustomerTenantCompanyReq {
    @Schema(description = "임대차 업체명", example = "하이회사")
    private String tenantCompanyName;

    @Schema(description = "1월 전기사용량", example = "50")
    private Long januaryElectricUsage;

    @Schema(description = "8월 전기사용량", example = "500")
    private Long augustElectricUsage;
}
