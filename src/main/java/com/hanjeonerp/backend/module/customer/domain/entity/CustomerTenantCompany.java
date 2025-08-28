package com.hanjeonerp.backend.module.customer.domain.entity;

import com.hanjeonerp.backend.core.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer_tenant_company")
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CustomerTenantCompany extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "tenant_company_name", nullable = true)
    private String tenantCompanyName;

    @Column(name = "january_electric_usage", nullable = true)
    private Long januaryElectricUsage;

    @Column(name = "august_electric_usage", nullable = true)
    private Long augustElectricUsage;
}
