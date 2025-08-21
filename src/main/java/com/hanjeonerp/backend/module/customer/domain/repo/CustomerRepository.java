package com.hanjeonerp.backend.module.customer.domain.repo;

import com.hanjeonerp.backend.module.customer.domain.entity.Customer;
import com.hanjeonerp.backend.module.customer.domain.vo.ProgressStatus;
import com.hanjeonerp.backend.module.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByCompanyName(String companyName);

    Long countByProgressStatus(ProgressStatus progressStatus);

    List<Customer> findByCreatedAtAfterOrUpdatedAtAfter(LocalDateTime createdAtAfter, LocalDateTime updatedAtAfter);

    List<Customer> findBySalesmanId(User salesmanId);

    List<Customer> findByEngineerId(User engineerId);
}
