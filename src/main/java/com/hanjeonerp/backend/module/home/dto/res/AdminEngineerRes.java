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
public class AdminEngineerRes {
    @Schema(description = "엔지니어 리스트", example = "")
    List<AdminEngineer> adminEngineerList;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AdminEngineer {
        @Schema(description = "id", example = "")
        private Long id;

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
