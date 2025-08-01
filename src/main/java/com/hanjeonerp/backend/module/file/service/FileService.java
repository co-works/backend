package com.hanjeonerp.backend.module.file.service;

import com.hanjeonerp.backend.module.file.domain.vo.FileCategory;
import com.hanjeonerp.backend.module.file.dto.req.GenerateFileUploadUrlReq;
import com.hanjeonerp.backend.module.file.dto.req.GenerateFileViewUrlReq;
import com.hanjeonerp.backend.module.file.dto.res.GenerateFileUploadUrlRes;
import com.hanjeonerp.backend.module.file.dto.res.GenerateFileViewUrlRes;
import com.hanjeonerp.backend.module.file.dto.res.GetFileCodeRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    @Value("${aws.bucket}")
    private String bucketName;

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    // 업로드 URL 생성
    public GenerateFileUploadUrlRes generateFileUploadUrl(GenerateFileUploadUrlReq req) {

        List<GenerateFileUploadUrlRes.UploadUrlRes> uploadUrlResList = new ArrayList<>();

        for (GenerateFileUploadUrlReq.UploadUrlReq item : req.getUploadUrlReqList()) {
            // 0. 파일 키 생성
            String fileId = UUID.randomUUID().toString();
            FileCategory category = item.getCategory(); // 파일카테고리
            String extension = item.getExtension(); // 확장자
            String fileKey = category + "/" + fileId + "." + extension;

            // 1. 업로드할 객체의 정보
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileKey)
                    .contentType(item.getContentType())
                    .build();

            // 2. URL 생성 정보(유효시간 30분)
            PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder()
                    .putObjectRequest(putObjectRequest)
                    .signatureDuration(Duration.ofMinutes(30))
                    .build();

            // 3. URL 생성
            String uploadUrl = s3Presigner.presignPutObject(putObjectPresignRequest).url().toString();

            GenerateFileUploadUrlRes.UploadUrlRes uploadUrlRes = GenerateFileUploadUrlRes.UploadUrlRes.builder()
                    .clientId(item.getClientId())
                    .fileKey(fileKey)
                    .uploadUrl(uploadUrl)
                    .build();
            uploadUrlResList.add(uploadUrlRes);
        }

        return GenerateFileUploadUrlRes.builder()
                .uploadUrlResList(uploadUrlResList)
                .build();
    }

    // 조회용 URL 생성
    public GenerateFileViewUrlRes generateFileViewURL(GenerateFileViewUrlReq req) {
        List<GenerateFileViewUrlRes.ViewUrlRes> viewUrlResList = new ArrayList<>();

        for (GenerateFileViewUrlReq.ViewUrlReq item : req.getViewUrlReqList()) {
            // 1. 조회할 객체의 정보
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(item.getFileKey())
                    .build();

            // 2. URL 생성 정보(유효시간 30분)
            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .getObjectRequest(getObjectRequest)
                    .signatureDuration(Duration.ofMinutes(30))
                    .build();

            // 3. URL 생성
            PresignedGetObjectRequest presignedUrl = s3Presigner.presignGetObject(presignRequest);
            String viewUrl = presignedUrl.url().toString();

            GenerateFileViewUrlRes.ViewUrlRes viewUrlRes = GenerateFileViewUrlRes.ViewUrlRes.builder()
                    .fileId(item.getFileId())
                    .fileKey(item.getFileKey())
                    .fileUrl(viewUrl)
                    .build();
            viewUrlResList.add(viewUrlRes);
        }

        return GenerateFileViewUrlRes.builder()
                .viewUrlResList(viewUrlResList)
                .build();
    }

    // S3 파일 삭제
    public String deleteFile(String fileKey) {
        // 1. 삭제 요청 생성
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .build();

        // 2. 삭제 실행
        s3Client.deleteObject(deleteObjectRequest);

        // 3. 삭제된 파일키
        return "Deleted fileKey: " + fileKey;
    }

    public GetFileCodeRes getFileCode() {
        // 파일 유형 리스트
        List<GetFileCodeRes.FileCategoryCode> fileCategoryCodeList = Arrays.stream(FileCategory.values())
                .map(type -> GetFileCodeRes.FileCategoryCode.builder()
                        .code(type.name())
                        .label(type.getLabel())
                        .build())
                .toList();

        return GetFileCodeRes.builder()
                .fileCategoryCodeList(fileCategoryCodeList)
                .build();
    }
}
