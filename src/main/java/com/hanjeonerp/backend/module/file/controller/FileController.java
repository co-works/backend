package com.hanjeonerp.backend.module.file.controller;

import com.hanjeonerp.backend.core.common.ApiResponse;
import com.hanjeonerp.backend.module.file.dto.req.GenerateFileUploadUrlReq;
import com.hanjeonerp.backend.module.file.dto.req.GenerateFileViewUrlReq;
import com.hanjeonerp.backend.module.file.dto.res.GenerateFileUploadUrlRes;
import com.hanjeonerp.backend.module.file.dto.res.GenerateFileViewUrlRes;
import com.hanjeonerp.backend.module.file.dto.res.GetFileCodeRes;
import com.hanjeonerp.backend.module.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
@Tag(name = "File", description = "파일 관련 컨트롤러")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    @Operation(summary = "파일 업로드용 URL 발급")
    public ApiResponse<GenerateFileUploadUrlRes> generateFileUploadUrl(@RequestBody GenerateFileUploadUrlReq req) {
        return ApiResponse.success(fileService.generateFileUploadUrl(req));
    }

    @PostMapping("/view")
    @Operation(summary = "파일 조회용 URL 발급(파일 url 만료 시 사용)")
    public ApiResponse<GenerateFileViewUrlRes> generateFileViewUrl(@RequestBody GenerateFileViewUrlReq req) {
        return ApiResponse.success(fileService.generateFileViewURL(req));
    }

    @DeleteMapping
    @Operation(summary = "s3에서 파일 삭제(사용자가 저장전에 첨부파일 제거 시 사용)")
    public ApiResponse<String> deleteFile(@RequestParam String fileKey) {
        return ApiResponse.success(fileService.deleteFile(fileKey));
    }

    @GetMapping("/code")
    @Operation(summary = "파일에서 사용하는 코드 모음")
    public ApiResponse<GetFileCodeRes> fileCode() {
        return ApiResponse.success(fileService.getFileCode());
    }
}
