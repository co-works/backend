package com.hanjeonerp.backend.module.customer.domain.entity;

import com.hanjeonerp.backend.core.common.BaseTimeEntity;
import com.hanjeonerp.backend.module.customer.domain.vo.BuildingType;
import com.hanjeonerp.backend.module.customer.domain.vo.ProgressStatus;
import com.hanjeonerp.backend.module.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "customer")
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Customer extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = true)
    private Long id;

    //고객사 정보
    @Column(name = "company_name", nullable = true)
    private String companyName; //업체명
    @Column(name = "representative", nullable = true)
    private String representative; //대표자
    @Column(name = "business_number", nullable = true)
    private String businessNumber; //사업자등록번호
    @Column(name = "business_type", nullable = true)
    private String businessType; //업태
    @Column(name = "business_item", nullable = true)
    private String businessItem; //업종
    @Column(name = "business_address", nullable = true)
    private String businessAddress; //사업장 소세지

    // 담당자 정보
    @Column(name = "manager_name", nullable = true)
    private String managerName; //담당자명
    @Column(name = "company_phone", nullable = true)
    private String companyPhone; //회사 전화번호
    @Column(name = "email", nullable = true)
    private String email; //이메일
    @Column(name = "phone_number", nullable = true)
    private String phoneNumber; //휴대폰 번호

    //한전 파워플래너 정보
    @Column(name = "power_planner_id", nullable = true)
    private String powerPlannerId; //한전 파워플래너 ID
    @Column(name = "power_planner_password", nullable = true)
    private String powerPlannerPassword; //한전 파워플래너 비밀번호

    @Column(name = "building_type", nullable = true)
    @Enumerated(EnumType.STRING)
    private BuildingType buildingType; //건축물 형태

    @Column(name = "is_tenant_factory", nullable = true)
    private boolean isTenantFactory; //임대차 공장 여부

    @Column(name = "january_electric_usage", nullable = true)
    private Long januaryElectricUsage; // 1월 전기사용량

    @Column(name = "august_electric_usage", nullable = true)
    private Long augustElectricUsage; // 8월 전기사용량

    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩 N+1 방지
    @JoinColumn(name = "salesman_id", nullable = true)
    private User salesmanId; //영업 담당자 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "engineer_id", nullable = true)
    private User engineerId; //담당 엔지니어 ID

    @Column(name = "project_cost", nullable = true)
    private BigDecimal projectCost; // 사업비용

    @Column(name = "electricity_saving_rate", nullable = true)
    private Double electricitySavingRate; // 전기요금 절감율(%)

    @Column(name = "subsidy", nullable = true)
    private Long subsidy; // 한전수불금

    @Column(name = "project_period", nullable = true)
    private String projectPeriod; // 수행기간

    @Column(name = "progress_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProgressStatus progressStatus; //진행 상태
}
