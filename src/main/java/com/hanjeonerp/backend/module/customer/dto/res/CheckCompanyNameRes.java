package com.hanjeonerp.backend.module.customer.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckCompanyNameRes {
    @Schema(description = "가능여부", example = "")
    private Boolean possible;

    @Schema(description = "영업담당자명", example = "")
    private String salesmanName;

    @Schema(description = "영업담당자 번호", example = "")
    private String salesmanPhoneNumber;

    @Schema(description = "영업담당자 이메일", example = "")
    private String salesmanEmail;
}
