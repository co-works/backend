package com.hanjeonerp.backend.module.customer.dto.req;

import com.hanjeonerp.backend.module.customer.domain.vo.BuildingType;
import com.hanjeonerp.backend.module.customer.domain.vo.ProgressStatus;
import com.hanjeonerp.backend.module.file.domain.vo.FileCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UpdateCustomerReq {
    @Schema(description = "업체명", example = "")
    private String companyName;

    @Schema(description = "대표자", example = "")
    private String representative;

    @Schema(description = "사업자등록번호", example = "")
    private String businessNumber;

    @Schema(description = "업태", example = "")
    private String businessType;

    @Schema(description = "업종", example = "")
    private String businessItem;

    @Schema(description = "사업장 소재지", example = "")
    private String businessAddress;

    // 담당자 정보
    @Schema(description = "담당자명", example = "")
    private String managerName;

    @Schema(description = "회사 전화번호", example = "")
    private String companyPhone;

    @Schema(description = "이메일", example = "")
    private String email;

    @Schema(description = "휴대폰 번호", example = "")
    private String phoneNumber;

    //한전 파워플래너 정보
    @Schema(description = "한전 파워플래너 ID", nullable = false)
    private String powerPlannerId;

    @Schema(description = "한전 파워플래너 PW", example = "")
    private String powerPlannerPassword; //한전 파워플래너 비밀번호

    @Schema(description = "건축물 형태", example = "")
    private BuildingType buildingType; //건축물 형태 (공장, 지식산업센터, 빌딩, 주상복합, 아파트단지(공동주택), 학교, 호텔, 기타)

    @Schema(description = "임대차 공장 여부", example = "")
    private boolean isTenantFactory; //임대차 공장 여부

    @Schema(description = "1월 전기사용량", example = "")
    private Long januaryElectricUsage;

    @Schema(description = "8월 전기사용량", example = "")
    private Long augustElectricUsage;

    @Schema(description = "영업 담당자 ID", example = "5")
    private Long salesmanId; //영업 담당자 ID

    @Schema(description = "담당 엔지니어 ID", example = "")
    private Long engineerId; //담당 엔지니어 ID

    @Schema(description = "사업비용", example = "")
    private BigDecimal projectCost; // 사업비용

    @Schema(description = "전기요금 절감율(%)", example = "")
    private Double electricitySavingRate; // 전기요금 절감율(%)

    @Schema(description = "한전수불금", example = "")
    private Long subsidy; // 한전수불금

    @Schema(description = "수행기간", example = "")
    private String projectPeriod; // 수행기간

    @Schema(description = "진행 상태", example = "")
    private ProgressStatus progressStatus; //진행 상태 (타당성 검토 의뢰, 실사, 실사 보고서, 계약, 시공, 사업확인서, 수수료 정산, 반려, 비고 등)

    @Schema(description = "새로운 첨부파일 리스트", example = "")
    List<newAttachmentFile> newAttachmentFileList;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class newAttachmentFile {
        @Schema(description = "파일 키", example = "")
        private String fileKey;

        @Schema(description = "파일 카테고리", example = "")
        private FileCategory category;

        @Schema(description = "원본 파일명", example = "")
        private String originalFileName;

        @Schema(description = "확장자", example = "")
        private String extension;

        @Schema(description = "콘텐츠 타입", example = "")
        private String contentType;

        @Schema(description = "사이즈", example = "")
        private Long size;
    }

    @Schema(description = "삭제 첨부파일 리스트(fileId)", example = "")
    List<Long> deleteAttachmentFileList;
}
