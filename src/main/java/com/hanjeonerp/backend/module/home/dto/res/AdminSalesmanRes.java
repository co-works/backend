package com.hanjeonerp.backend.module.home.dto.res;

import com.hanjeonerp.backend.module.user.domain.vo.SettlementMethod;
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
public class AdminSalesmanRes {
    @Schema(description = "영업사원 리스트", example = "")
    private List<AdminSalesman> adminSalesmanList;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AdminSalesman {
        @Schema(description = "id", example = "")
        private Long id;

        @Schema(description = "userId", example = "")
        private String userId;

        @Schema(description = "userPw", example = "")
        private String userPw;

        @Schema(description = "이름", example = "")
        private String name;

        @Schema(description = "휴대전화", example = "")
        private String phoneNumber;

        @Schema(description = "이메일", example = "")
        private String email;

        @Schema(description = "주소", example = "")
        private String address;

        @Schema(description = "수수료율", example = "")
        private Double commissionRate;

        @Schema(description = "정산방법", example = "")
        private SettlementMethod settlementMethod;

        @Schema(description = "은행명", example = "")
        private String bankName;

        @Schema(description = "은행계좌", example = "")
        private String bankAccount;

        @Schema(description = "사업자번호", example = "")
        private String businessNumber;
    }
}
