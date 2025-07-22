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


    @Enumerated(EnumType.STRING)
    private FileCategory category; // 예: 사업자등록증, 도면 등

    private Long customerId; // 업체 ID

    private String originalFilename; // 사용자 업로드 원본 이름

    private String storedFilename;   // 서버에 저장된 UUID 기반 이름

    @Column(nullable = false)
    private String path;             // ex. /uploads/customer/123/

    private String extension;        // ex. jpg, png, xlsx

    private Long size;               // 파일 크기 (bytes)

    private String contentType;      // ex. image/jpeg, application/vnd.ms-excel



    public File(FileCategory category,
                      Long customer,
                      String originalFilename,
                      String storedFilename,
                      String path,
                      String extension,
                      Long size,
                      String contentType) {

        this.category = category;
        this.customerId = customer;
        this.originalFilename = originalFilename;
        this.storedFilename = storedFilename;
        this.path = path;
        this.extension = extension;
        this.size = size;
        this.contentType = contentType;
    }

    public String getFullPath() {
        return path + storedFilename;
    }
}
