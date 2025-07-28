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
public class GenerateViewUrlRes {
    private List<ViewUrlRes> viewUrlResList;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class ViewUrlRes {
        private Long fileId;

        private String fileKey;

        private String viewUrl;
    }
}
