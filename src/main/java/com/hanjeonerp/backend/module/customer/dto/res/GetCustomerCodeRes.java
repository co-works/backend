package com.hanjeonerp.backend.module.customer.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetCustomerCodeRes {
    @Schema(description = "빌딩 코드 리스트", example = "")
    private List<BuildingCode> buildingCodeList;

    @Schema(description = "진행상황 코드 리스트", example = "")
    private List<ProgressCode> progressCodeList;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class BuildingCode {
        private String code;
        private String label;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ProgressCode {
        private String code;
        private String label;
    }
}
