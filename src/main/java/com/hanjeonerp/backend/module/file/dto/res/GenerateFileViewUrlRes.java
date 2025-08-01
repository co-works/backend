package com.hanjeonerp.backend.module.file.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GenerateFileViewUrlRes {
    private List<ViewUrlRes> viewUrlResList;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ViewUrlRes {
        private Long fileId;

        private String fileKey;

        private String fileUrl;
    }
}
