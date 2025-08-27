package com.hanjeonerp.backend.module.file.domain.repo;

import com.hanjeonerp.backend.module.customer.domain.entity.Customer;
import com.hanjeonerp.backend.module.file.domain.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findAllByCustomer(Customer customer);

    File findByFileKey(String fileKey);
}
