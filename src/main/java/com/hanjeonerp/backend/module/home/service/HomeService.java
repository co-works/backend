package com.hanjeonerp.backend.module.home.service;

import com.hanjeonerp.backend.core.exception.BadRequestException;
import com.hanjeonerp.backend.core.exception.ErrorCode;
import com.hanjeonerp.backend.core.util.CryptoUtil;
import com.hanjeonerp.backend.module.customer.domain.entity.Customer;
import com.hanjeonerp.backend.module.customer.domain.repo.CustomerRepository;
import com.hanjeonerp.backend.module.customer.domain.vo.ProgressStatus;
import com.hanjeonerp.backend.module.home.dto.res.*;
import com.hanjeonerp.backend.module.user.domain.entity.User;
import com.hanjeonerp.backend.module.user.domain.repo.UserRepo;
import com.hanjeonerp.backend.module.user.domain.vo.Role;
import com.hanjeonerp.backend.module.user.domain.vo.SalesmanProfile;
import com.hanjeonerp.backend.module.user.domain.vo.UserBasicProfile;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final CryptoUtil cryptoUtil;

    private final UserRepo userRepository;
    private final CustomerRepository customerRepository;

    public AdminDashboardRes adminDashboard() {
        Long customerCount = customerRepository.countByIsDelete(false);
        Long salesmanCount = userRepository.countByRoleAndIsDeleted(Role.SALESMAN, false);
        Long engineerCount = userRepository.countByRoleAndIsDeleted(Role.ENGINEER, false);
        Long inProgressCount = customerRepository.countByProgressStatusAndIsDelete(ProgressStatus.IN_PROGRESS, false);

        // 7일 이내 등록되거나 수정된 수용가 조회
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        List<Customer> list = customerRepository.findByCreatedAtAfterOrUpdatedAtAfterAndIsDelete(sevenDaysAgo, sevenDaysAgo, false);

        // res build
        List<AdminDashboardRes.RecentCustomer> recentCustomerList = new ArrayList<>();

        for (Customer item : list) {
            AdminDashboardRes.RecentCustomer recentCustomer = AdminDashboardRes.RecentCustomer.builder()
                    .customerId(item.getId())
                    .companyName(item.getCompanyName())
                    .managerName(item.getManagerName())
                    .progressStatus(item.getProgressStatus())
                    .build();
            recentCustomerList.add(recentCustomer);
        }

        return AdminDashboardRes.builder()
                .customerCount(customerCount)
                .salesmanCount(salesmanCount)
                .engineerCount(engineerCount)
                .inProgressCount(inProgressCount)
                .recentCustomerList(recentCustomerList)
                .build();
    }

    public AdminSalesmanRes adminSalesman() {
        List<User> list = userRepository.findByRoleAndIsDeleted(Role.SALESMAN, false);

        List<AdminSalesmanRes.AdminSalesman> adminSalesmanList = new ArrayList<>();
        for (User item : list) {
            AdminSalesmanRes.AdminSalesman adminSalesman = AdminSalesmanRes.AdminSalesman.builder()
                    .id(item.getId())
                    .userId(item.getUsername())
                    .userPw(cryptoUtil.decrypt(item.getPassword()))
                    .name(Optional.ofNullable(item.getBasicProfile()).map(UserBasicProfile::getName).orElse(null))
                    .phoneNumber(Optional.ofNullable(item.getBasicProfile()).map(UserBasicProfile::getPhone).orElse(null))
                    .email(Optional.ofNullable(item.getBasicProfile()).map(UserBasicProfile::getEmail).orElse(null))
                    .address(Optional.ofNullable(item.getBasicProfile()).map(UserBasicProfile::getAddress).orElse(null))
                    .commissionRate(Optional.ofNullable(item.getSalesmanProfile()).map(SalesmanProfile::getCommissionRate).orElse(null))
                    .settlementMethod(Optional.ofNullable(item.getSalesmanProfile()).map(SalesmanProfile::getSettlementMethod).orElse(null))
                    .bankName(Optional.ofNullable(item.getSalesmanProfile()).map(SalesmanProfile::getBankName).orElse(null))
                    .bankAccount(Optional.ofNullable(item.getSalesmanProfile()).map(SalesmanProfile::getBankAccount).orElse(null))
                    .businessNumber(Optional.ofNullable(item.getSalesmanProfile()).map(SalesmanProfile::getBusinessNumber).orElse(null))
                    .build();
            adminSalesmanList.add(adminSalesman);
        }

        return AdminSalesmanRes.builder()
                .adminSalesmanList(adminSalesmanList)
                .build();
    }

    public AdminEngineerRes adminEngineer() {
        List<User> list = userRepository.findByRoleAndIsDeleted(Role.ENGINEER, false);

        List<AdminEngineerRes.AdminEngineer> adminEngineerList = new ArrayList<>();
        for (User item : list) {
            AdminEngineerRes.AdminEngineer adminEngineer = AdminEngineerRes.AdminEngineer.builder()
                    .id(item.getId())
                    .userId(item.getUsername())
                    .userPw(cryptoUtil.decrypt(item.getPassword()))
                    .name(Optional.ofNullable(item.getBasicProfile()).map(UserBasicProfile::getName).orElse(null))
                    .phoneNumber(Optional.ofNullable(item.getBasicProfile()).map(UserBasicProfile::getPhone).orElse(null))
                    .email(Optional.ofNullable(item.getBasicProfile()).map(UserBasicProfile::getEmail).orElse(null))
                    .address(Optional.ofNullable(item.getBasicProfile()).map(UserBasicProfile::getAddress).orElse(null))
                    .build();
            adminEngineerList.add(adminEngineer);
        }

        return AdminEngineerRes.builder()
                .adminEngineerList(adminEngineerList)
                .build();
    }

    public AdminCustomerRes adminCustomer() {
        List<Customer> list = customerRepository.findByIsDelete(false);

        List<AdminCustomerRes.AdminCustomer> customerList = new ArrayList<>();
        for (Customer item : list) {
            AdminCustomerRes.AdminCustomer adminCustomer = AdminCustomerRes.AdminCustomer.builder()
                    .customerId(item.getId())
                    .companyName(item.getCompanyName())
                    .representative(item.getRepresentative())
                    .buildingType(item.getBuildingType())
                    .salesmanName(item.getSalesmanId() != null ? item.getSalesmanId().getBasicProfile().getName() : null)
                    .engineerName(item.getEngineerId() != null ? item.getEngineerId().getBasicProfile().getName() : null)
                    .companyEmail(item.getEmail())
                    .companyPhone(item.getCompanyPhone())
                    .build();
            customerList.add(adminCustomer);
        }

        return AdminCustomerRes.builder()
                .customerList(customerList)
                .build();
    }

    public UserCustomerRes userCustomer(Long userId, Role role) {
        // 담당 영업사
        if (role.equals(Role.SALESMAN)) {
            User salesman = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("영업자가 존재하지 않습니다."));

            List<Customer> list = customerRepository.findBySalesmanIdAndIsDelete(salesman, false);

            List<UserCustomerRes.UserCustomer> userCustomerList = new ArrayList<>();
            for (Customer item : list) {
                UserCustomerRes.UserCustomer userCustomer = UserCustomerRes.UserCustomer.builder()
                        .customerId(item.getId())
                        .companyName(item.getCompanyName())
                        .representative(item.getRepresentative())
                        .buildingType(item.getBuildingType())
                        .salesmanName(item.getSalesmanId() != null ? item.getSalesmanId().getBasicProfile().getName() : null)
                        .engineerName(item.getEngineerId() != null ? item.getEngineerId().getBasicProfile().getName() : null)
                        .progressStatus(item.getProgressStatus())
                        .companyPhone(item.getCompanyPhone())
                        .companyEmail(item.getEmail())
                        .build();
                userCustomerList.add(userCustomer);
            }

            return UserCustomerRes.builder()
                    .customerList(userCustomerList)
                    .build();
        } else if (role.equals(Role.ENGINEER)) { // 담당 기술자
            User engineer = userRepository.findById(userId).orElseThrow(() -> new BadRequestException(ErrorCode.USER_NOT_FOUND));

            List<Customer> list = customerRepository.findByEngineerIdAndIsDelete(engineer, false);

            List<UserCustomerRes.UserCustomer> userCustomerList = new ArrayList<>();
            for (Customer item : list) {
                UserCustomerRes.UserCustomer userCustomer = UserCustomerRes.UserCustomer.builder()
                        .customerId(item.getId())
                        .companyName(item.getCompanyName())
                        .representative(item.getRepresentative())
                        .buildingType(item.getBuildingType())
                        .salesmanName(item.getSalesmanId() != null ? item.getSalesmanId().getBasicProfile().getName() : null)
                        .engineerName(item.getEngineerId() != null ? item.getEngineerId().getBasicProfile().getName() : null)
                        .progressStatus(item.getProgressStatus())
                        .companyPhone(item.getCompanyPhone())
                        .companyEmail(item.getEmail())
                        .build();
                userCustomerList.add(userCustomer);
            }

            return UserCustomerRes.builder()
                    .customerList(userCustomerList)
                    .build();
        } else {
            return UserCustomerRes.builder()
                    .customerList(Collections.emptyList())
                    .build();
        }
    }
}
