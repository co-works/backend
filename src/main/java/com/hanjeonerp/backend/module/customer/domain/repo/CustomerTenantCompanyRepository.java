package com.hanjeonerp.backend.module.customer.domain.repo;

import com.hanjeonerp.backend.module.customer.domain.entity.Customer;
import com.hanjeonerp.backend.module.customer.domain.entity.CustomerTenantCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerTenantCompanyRepository extends JpaRepository<CustomerTenantCompany, Long> {
    List<CustomerTenantCompany> findByCustomer(Customer customer);

    List<CustomerTenantCompany> findAllByCustomer(Customer customer);
}
