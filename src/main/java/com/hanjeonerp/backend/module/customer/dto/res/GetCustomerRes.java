package com.hanjeonerp.backend.module.customer.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanjeonerp.backend.module.customer.domain.vo.BuildingType;
import com.hanjeonerp.backend.module.customer.domain.vo.ProgressStatus;
import com.hanjeonerp.backend.module.file.domain.vo.FileCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCustomerRes {
    @Schema(description = "업체명", example = "")
    private String companyName;

    @Schema(description = "대표자", example = "")
    private String representative;

    @Schema(description = "사업자등록번호", example = "")
    @NotNull
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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BuildingType buildingType; //건축물 형태 (공장, 지식산업센터, 빌딩, 주상복합, 아파트단지(공동주택), 학교, 호텔, 기타)

    @Schema(description = "임대차 공장 여부", example = "")
    private boolean isTenantFactory; //임대차 공장 여부

    @Schema(description = "1월 전기사용량", example = "")
    private Long januaryElectricUsage;

    @Schema(description = "8월 전기사용량", example = "")
    private Long augustElectricUsage;

    @Schema(description = "영업 담당자 ID", example = "5")
    private Long salesmanId; //영업 담당자 ID

    @Schema(description = "영업 담당자명", example = "")
    private String salesmanName;

    @Schema(description = "영업 담당자 수수료율", example = "")
    private Double salesmanCommissionRate;

    @Schema(description = "영업 담당자 번호", example = "")
    private String salesmanPhoneNumber;

    @Schema(description = "영업 담당자 이메일", example = "")
    private String salesmanEmail;

    @Schema(description = "담당 엔지니어 ID", example = "")
    private Long engineerId; //담당 엔지니어 ID

    @Schema(description = "담당 엔지니어명", example = "")
    private String engineerName;

    @Schema(description = "담당 엔지니어 번호", example = "")
    private String engineerPhoneNumber;

    @Schema(description = "담당 엔지니어 이메일", example = "")
    private String engineerEmail;

    @Schema(description = "사업비용", example = "")
    private BigDecimal projectCost; // 사업비용

    @Schema(description = "전기요금 절감율(%)", example = "")
    private Double electricitySavingRate; // 전기요금 절감율(%)

    @Schema(description = "한전수불금", example = "")
    private Long subsidy; // 한전수불금

    @Schema(description = "수행기간", example = "")
    private String projectPeriod; // 수행기간

    @Schema(description = "진행 상태", example = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ProgressStatus progressStatus; //진행 상태 (타당성 검토 의뢰, 실사, 실사 보고서, 계약, 시공, 사업확인서, 수수료 정산, 반려, 비고 등)

    @Schema(description = "첨부파일 리스트", example = "")
    List<CustomerFile> customerFileList;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomerFile {
        @Schema(description = "파일 ID", example = "")
        private Long fileId;

        @Schema(description = "발주서 ID", example = "")
        private Long customerId;

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

        @Schema(description = "파일 주소", example = "")
        private String fileUrl;
    }
}
