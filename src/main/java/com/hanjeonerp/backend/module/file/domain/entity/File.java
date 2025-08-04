package com.hanjeonerp.backend.module.file.domain.entity;

import com.hanjeonerp.backend.core.common.BaseTimeEntity;
import com.hanjeonerp.backend.module.customer.domain.entity.Customer;
import com.hanjeonerp.backend.module.file.domain.vo.FileCategory;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "file")
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class File extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "file_key", nullable = false)
    private String fileKey;   // S3 파일 키

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private FileCategory category; // 예: 사업자등록증, 도면 등

    @Column(name = "original_file_name", nullable = false)
    private String originalFilename; // 사용자 업로드 원본 이름

    @Column(name = "extension", nullable = false)
    private String extension;        // ex. jpg, png, xlsx

    @Column(name = "content_type", nullable = false)
    private String contentType;      // ex. image/jpeg, application/vnd.ms-excel

    @Column(name = "size", nullable = false)
    private Long size;               // 파일 크기 (bytes)
}

