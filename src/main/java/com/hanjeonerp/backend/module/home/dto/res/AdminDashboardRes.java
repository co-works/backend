package com.hanjeonerp.backend.module.home.dto.res;

import com.hanjeonerp.backend.module.customer.domain.vo.ProgressStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminDashboardRes {
    @Schema(description = "총 수용가 수", example = "")
    private Long customerCount;

    @Schema(description = "활성 영업자 수", example = "")
    private Long salesmanCount;

    @Schema(description = "등록 기술자 수", example = "")
    private Long engineerCount;

    @Schema(description = "진행중인 사업 수", example = "")
    private Long inProgressCount;

    @Schema(description = "최근 등록된 수용가(오늘 날짜 기준 7일)", example = "")
    List<RecentCustomer> recentCustomerList;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RecentCustomer {
        @Schema(description = "수용가 id", example = "")
        private Long customerId;

        @Schema(description = "회사명", example = "")
        private String companyName;

        @Schema(description = "담당자명", example = "")
        private String managerName;

        @Schema(description = "진행상태", example = "")
        private ProgressStatus progressStatus;
    }
}
