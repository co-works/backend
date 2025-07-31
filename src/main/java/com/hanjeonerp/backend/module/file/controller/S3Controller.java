package com.hanjeonerp.backend.module.file.controller;

import com.hanjeonerp.backend.core.common.ApiResponse;
import com.hanjeonerp.backend.module.file.dto.req.GenerateS3UploadUrlReq;
import com.hanjeonerp.backend.module.file.dto.req.GenerateS3ViewUrlReq;
import com.hanjeonerp.backend.module.file.dto.res.GenerateS3UploadUrlRes;
import com.hanjeonerp.backend.module.file.dto.res.GenerateS3ViewUrlRes;
import com.hanjeonerp.backend.module.file.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
@Tag(name = "FileUpload", description = "S3 관련 컨트롤러")
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/upload")
    @Operation(summary = "파일 업로드용 URL 발급")
    public ApiResponse<GenerateS3UploadUrlRes> generateS3UploadUrl(@RequestBody GenerateS3UploadUrlReq req) {
        return ApiResponse.success(s3Service.generateS3UploadUrl(req));
    }

    @PostMapping("/view")
    @Operation(summary = "파일 조회용 URL 발급(파일 url 만료 시 사용)")
    public ApiResponse<GenerateS3ViewUrlRes> generateS3ViewUrl(@RequestBody GenerateS3ViewUrlReq req) {
        return ApiResponse.success(s3Service.generateS3ViewURL(req));
    }

    @DeleteMapping
    @Operation(summary = "s3에서 파일 삭제(사용자가 저장전에 첨부파일 제거 시 사용)")
    public ApiResponse<String> deleteS3File(@RequestParam String fileKey) {
        return ApiResponse.success(s3Service.deleteS3File(fileKey));
    }
}
