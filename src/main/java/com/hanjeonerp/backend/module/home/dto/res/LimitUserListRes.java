package com.hanjeonerp.backend.module.home.dto.res;

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
public class LimitUserListRes {

    @Schema(description = "제한된 엔지니어 리스트", example = "")
    List<LimitEngineer> limitEngineerList;

    @Schema(description = "제한된 영업자 리스트", example = "")
    List<LimitSalesman> limitSalesmanList;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LimitEngineer {
        @Schema(description = "id", example = "")
        private Long id;

        @Schema(description = "userId", example = "")
        private String userId;

        @Schema(description = "이름", example = "")
        private String name;

        @Schema(description = "휴대전화", example = "")
        private String phoneNumber;

        @Schema(description = "이메일", example = "")
        private String email;

        @Schema(description = "주소", example = "")
        private String address;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LimitSalesman {
        @Schema(description = "id", example = "")
        private Long id;

        @Schema(description = "userId", example = "")
        private String userId;

        @Schema(description = "이름", example = "")
        private String name;

        @Schema(description = "휴대전화", example = "")
        private String phoneNumber;

        @Schema(description = "이메일", example = "")
        private String email;

        @Schema(description = "주소", example = "")
        private String address;
    }
}
