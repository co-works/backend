package com.hanjeonerp.backend.module.customer.domain.repo;

import com.hanjeonerp.backend.module.customer.domain.entity.Customer;
import com.hanjeonerp.backend.module.customer.domain.vo.ProgressStatus;
import com.hanjeonerp.backend.module.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByCompanyName(String companyName);

    Long countByIsDelete(Boolean isDelete);

    Long countByProgressStatusAndIsDelete(ProgressStatus progressStatus, Boolean isDelete);

    List<Customer> findByCreatedAtAfterOrUpdatedAtAfterAndIsDelete(LocalDateTime createdAtAfter, LocalDateTime updatedAtAfter, Boolean isDelete);

    List<Customer> findByIsDelete(Boolean isDelete);

    List<Customer> findBySalesmanIdAndIsDelete(User salesmanId, Boolean isDelete);

    List<Customer> findByEngineerIdAndIsDelete(User engineerId, Boolean isDelete);
}
