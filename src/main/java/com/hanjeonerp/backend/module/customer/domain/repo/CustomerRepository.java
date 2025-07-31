package com.hanjeonerp.backend.module.customer.domain.repo;

import com.hanjeonerp.backend.module.customer.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByCompanyName(String companyName);
}
