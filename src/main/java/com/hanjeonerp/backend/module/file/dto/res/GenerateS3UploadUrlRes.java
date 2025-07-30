package com.hanjeonerp.backend.module.file.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GenerateS3UploadUrlRes {

    private List<UploadUrlRes> uploadUrlResList;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class UploadUrlRes {
        private String clientId;

        private String fileKey;

        private String uploadUrl;
    }
}
