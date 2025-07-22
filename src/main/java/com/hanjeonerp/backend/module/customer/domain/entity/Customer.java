package com.hanjeonerp.backend.module.customer.domain.entity;

import com.hanjeonerp.backend.module.customer.domain.vo.BuildingType;
import com.hanjeonerp.backend.module.customer.domain.vo.ProgressStatus;
import com.hanjeonerp.backend.module.customer.domain.vo.TenantElectricUsage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    //고객사 정보
    private String companyName; //업체명
    private String representative; //대표자
    private String businessNumber; //사업자등록번호
    private String businessType; //업태
    private String businessItem; //업종
    private String businessAddress; //사업장 소세지

    // 담당자 정보
    private String managerName; //담당자명
    private String companyPhone; //회사 전화번호
    private String email; //이메일
    private String phoneNumber; //휴대폰 번호

    //한전 파워플래너 정보
    private String powerPlannerId; //한전 파워플래너 ID
    private String powerPlannerPassword; //한전 파워플래너 비밀번호

    @Enumerated(EnumType.STRING)
    private BuildingType buildingType; //건축물 형태 (공장, 지식산업센터, 빌딩, 주상복합, 아파트단지(공동주택), 학교, 호텔, 기타)

    private boolean isTenantFactory; //임대차 공장 여부

    @Enumerated(EnumType.STRING)
    private TenantElectricUsage tenantElectricUsage; //임대차 전기 사용량 (1월, 8월 등)

    private Long salesManId; //영업 담당자 ID

    private Long engineerId; //담당 엔지니어 ID

    private BigDecimal projectCost;
    private Double electricitySavingRate;
    private BigDecimal subsidy;
    private String projectPeriod;

    @Enumerated(EnumType.STRING)
    private ProgressStatus progressStatus; //진행 상태 (타당성 검토 의뢰, 실사, 실사 보고서, 계약, 시공, 사업확인서, 수수료 정산, 반려, 비고 등)

    //TODO: 추후에 첨부파일, 보고서 파일 등 추가

    private Long attachmentId; //첨부파일 ID
    private Long reportFileId; //보고서 파일 ID
}
