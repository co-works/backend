package com.hanjeonerp.backend.module.file.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GenerateFileViewUrlReq {
    List<ViewUrlReq> viewUrlReqList;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ViewUrlReq {
        Long fileId;

        String fileKey;
    }
}
