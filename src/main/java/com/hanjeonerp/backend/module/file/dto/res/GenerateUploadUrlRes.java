package com.hanjeonerp.backend.module.file.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GenerateUploadUrlRes {

    private List<UploadUrlRes> uploadUrlResList;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class UploadUrlRes {
        private String clientId;

        private String fileKey;

        private String uploadUrl;
    }
}
