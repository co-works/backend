package com.hanjeonerp.backend.module.file.domain.entity;

import com.hanjeonerp.backend.core.common.BaseTimeEntity;
import com.hanjeonerp.backend.module.file.domain.vo.FileCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class File extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String fileKey;   // S3 파일 키

    @Enumerated(EnumType.STRING)
    private FileCategory category; // 예: 사업자등록증, 도면 등

    private String originalFilename; // 사용자 업로드 원본 이름

    private String extension;        // ex. jpg, png, xlsx

    private String contentType;      // ex. image/jpeg, application/vnd.ms-excel

    private Long size;               // 파일 크기 (bytes)
}
