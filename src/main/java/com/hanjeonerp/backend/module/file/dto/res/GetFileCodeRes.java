package com.hanjeonerp.backend.module.file.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetFileCodeRes {
    @Schema(description = "파일 카테고리 코드 리스트", example = "")
    private List<FileCategoryCode> fileCategoryCodeList;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class FileCategoryCode {
        private String code;
        private String label;
    }
}
