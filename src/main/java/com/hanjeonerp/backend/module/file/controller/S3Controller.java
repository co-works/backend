package com.hanjeonerp.backend.module.file.controller;

import com.hanjeonerp.backend.core.common.ApiResponse;
import com.hanjeonerp.backend.module.file.dto.req.GenerateUploadUrlReq;
import com.hanjeonerp.backend.module.file.dto.req.GenerateViewUrlReq;
import com.hanjeonerp.backend.module.file.dto.res.GenerateUploadUrlRes;
import com.hanjeonerp.backend.module.file.dto.res.GenerateViewUrlRes;
import com.hanjeonerp.backend.module.file.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
@Tag(name = "FileUpload", description = "S3 관련 컨트롤러")
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/upload")
    @Operation(summary = "파일 업로드용 URL 발급")
    public ApiResponse<GenerateUploadUrlRes> generateUploadUrl(
//            @AuthenticationPrincipal Long userId,
                                                               @RequestBody GenerateUploadUrlReq req) {
        return ApiResponse.success(s3Service.generateUploadUrl(req));
    }



    @PostMapping("/view")
    @Operation(summary = "파일 조회용 URL 발급")
    public ApiResponse<GenerateViewUrlRes> generateViewUrl(
//            @AuthenticationPrincipal Long userId,
                                                           @RequestBody GenerateViewUrlReq req) {
        return ApiResponse.success(s3Service.generateViewURL(req));
    }
}
