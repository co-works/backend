package com.hanjeonerp.backend.module.file.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GenerateViewUrlRes {
    private List<ViewUrlRes> viewUrlResList;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ViewUrlRes {
        private Long fileId;

        private String fileKey;

        private String viewUrl;
    }
}
