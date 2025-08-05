package com.hanjeonerp.backend.module.home.dto.res;

import com.hanjeonerp.backend.module.customer.domain.vo.BuildingType;
import com.hanjeonerp.backend.module.customer.domain.vo.ProgressStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminCustomerRes {
    private List<AdminCustomer> customerList;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AdminCustomer {
        @Schema(description = "수용가 id", example = "")
        private Long customerId;

        @Schema(description = "회사명", example = "")
        private String companyName;

        @Schema(description = "대표자", example = "")
        private String representative;

        @Schema(description = "건물 형태", example = "")
        private BuildingType buildingType;

        @Schema(description = "담당 영업자명", example = "")
        private String salesmanName;

        @Schema(description = "담당 기술자명", example = "")
        private String engineerName;

        @Schema(description = "진행 상태", example = "")
        private ProgressStatus progressStatus;

        @Schema(description = "회사 휴대전화", example = "")
        private String companyPhone;

        @Schema(description = "회사 이메일", example = "")
        private String companyEmail;
    }
}
