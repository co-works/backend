package com.hanjeonerp.backend.module.file.dto.req;

import com.hanjeonerp.backend.module.file.domain.vo.FileCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GenerateS3UploadUrlReq {
    List<UploadUrlReq> uploadUrlReqList;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class UploadUrlReq {
        private String clientId;

        private FileCategory category;

        private String extension;

        private String contentType;
    }
}
