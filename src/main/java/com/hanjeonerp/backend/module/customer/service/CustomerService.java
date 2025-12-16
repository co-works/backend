package com.hanjeonerp.backend.module.customer.service;

import com.hanjeonerp.backend.core.util.CryptoUtil;
import com.hanjeonerp.backend.module.customer.domain.entity.Customer;
import com.hanjeonerp.backend.module.customer.domain.entity.CustomerTenantCompany;
import com.hanjeonerp.backend.module.customer.domain.repo.CustomerRepository;
import com.hanjeonerp.backend.module.customer.domain.repo.CustomerTenantCompanyRepository;
import com.hanjeonerp.backend.module.customer.domain.vo.BuildingType;
import com.hanjeonerp.backend.module.customer.domain.vo.ProgressStatus;
import com.hanjeonerp.backend.module.customer.dto.req.GenerateCustomerReq;
import com.hanjeonerp.backend.module.customer.dto.req.UpdateCustomerReq;
import com.hanjeonerp.backend.module.customer.dto.req.UpdateCustomerTenantCompanyReq;
import com.hanjeonerp.backend.module.customer.dto.res.*;
import com.hanjeonerp.backend.module.file.domain.entity.File;
import com.hanjeonerp.backend.module.file.domain.repo.FileRepository;
import com.hanjeonerp.backend.module.file.dto.req.GenerateFileViewUrlReq;
import com.hanjeonerp.backend.module.file.dto.res.GenerateFileViewUrlRes;
import com.hanjeonerp.backend.module.file.service.FileService;
import com.hanjeonerp.backend.module.user.domain.entity.User;
import com.hanjeonerp.backend.module.user.domain.repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CryptoUtil cryptoUtil;

    private final CustomerRepository customerRepository;
    private final CustomerTenantCompanyRepository customerTenantCompanyRepository;
    private final FileRepository fileRepository;
    private final UserRepo userRepo;

    private final FileService fileService;

    @Transactional
    public GetCustomerRes generateCustomer(GenerateCustomerReq req) {
        User salesman = null;

        if (req.getSalesmanId() != null) {
            salesman = userRepo.findById(req.getSalesmanId()).orElseThrow(() -> new EntityNotFoundException("영업사원이 존재하지 않습니다."));
        }

        User engineer = null;
        if (req.getEngineerId() != null) {
            engineer = userRepo.findById(req.getEngineerId()).orElseThrow(() -> new EntityNotFoundException("기술자가 존재하지 않습니다."));
        }

        // 파워 플래너 패스워드 암호화
        String cryptoPowerPlannerPassword = null;
        if (req.getPowerPlannerPassword() != null) {
            cryptoPowerPlannerPassword = cryptoUtil.encrypt(req.getPowerPlannerPassword());
        }

        // 수용가, 타당성검토의뢰서 저장
        Customer customer = Customer.builder()
                .companyName(req.getCompanyName())
                .representative(req.getRepresentative())
                .businessNumber(req.getBusinessNumber())
                .businessType(req.getBusinessType())
                .businessItem(req.getBusinessItem())
                .businessAddress(req.getBusinessAddress())
                .managerName(req.getManagerName())
                .companyPhone(req.getCompanyPhone())
                .email(req.getEmail())
                .phoneNumber(req.getPhoneNumber())
                .powerPlannerId(req.getPowerPlannerId())
                .powerPlannerPassword(cryptoPowerPlannerPassword)
                .buildingType(req.getBuildingType())
                .isTenantFactory(req.isTenantFactory())
                .salesmanId(salesman)
                .engineerId(engineer)
                .projectCost(req.getProjectCost())
                .electricitySavingRate(req.getElectricitySavingRate())
                .subsidy(req.getSubsidy())
                .projectPeriod(req.getProjectPeriod())
                .memo(req.getMemo())
                .progressStatus(req.getProgressStatus())
                .build();
        customerRepository.save(customer);

        // 임대차 업체 저장
        if (!req.getTenantCompanyList().isEmpty()) {
            for (GenerateCustomerReq.TenantCompany item : req.getTenantCompanyList()) {
                CustomerTenantCompany tenantCompany = CustomerTenantCompany.builder()
                        .customer(customer)
                        .tenantCompanyName(item.getTenantCompanyName())
                        .januaryElectricUsage(item.getJanuaryElectricUsage())
                        .augustElectricUsage(item.getAugustElectricUsage())
                        .build();
                customerTenantCompanyRepository.save(tenantCompany);
            }
        }

        // 파일 저장
        List<GenerateCustomerReq.AttachmentFile> fileList = req.getAttachmentFileList();
        if (!fileList.isEmpty()) {
            for (GenerateCustomerReq.AttachmentFile item : fileList) {
                File file = File.builder()
                        .customer(customer)
                        .fileKey(item.getFileKey())
                        .category(item.getCategory())
                        .originalFileName(item.getOriginalFileName())
                        .extension(item.getExtension())
                        .contentType(item.getContentType())
                        .size(item.getSize())
                        .build();
                fileRepository.save(file);
            }
        }

        return getCustomer(customer.getId());
    }

    @Transactional(readOnly = true)
    public GetCustomerRes getCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("의뢰서가 존재하지 않습니다."));

        if (customer == null) {
            return null;
        }

        User salesman = customer.getSalesmanId();
        User engineer = customer.getEngineerId();

        // 임대차 업체 조회
        List<CustomerTenantCompany> tenantCompanyList = customerTenantCompanyRepository.findByCustomer(customer);
        List<GetCustomerRes.TenantCompany> tenantCompanyResList = new ArrayList<>();
        if (!tenantCompanyList.isEmpty()) {
            tenantCompanyResList = tenantCompanyList.stream()
                    .map(tenantCompany -> GetCustomerRes.TenantCompany.builder()
                            .customerTenantCompanyId(tenantCompany.getId())
                            .tenantCompanyName(tenantCompany.getTenantCompanyName())
                            .januaryElectricUsage(tenantCompany.getJanuaryElectricUsage())
                            .augustElectricUsage(tenantCompany.getAugustElectricUsage())
                            .build())
                    .toList();
        }

        // 파일 조회
        List<File> savedFileList = fileRepository.findAllByCustomer(customer);

        List<GetCustomerRes.CustomerFile> fileResList = new ArrayList<>();
        if (!savedFileList.isEmpty()) {
            List<GenerateFileViewUrlReq.ViewUrlReq> viewUrlReqList = savedFileList.stream()
                    .map(file -> new GenerateFileViewUrlReq.ViewUrlReq(file.getId(), file.getFileKey()))
                    .toList();

            GenerateFileViewUrlReq viewUrlReq = new GenerateFileViewUrlReq(viewUrlReqList);
            List<GenerateFileViewUrlRes.ViewUrlRes> viewUrlResList = fileService.generateFileViewURL(viewUrlReq).getViewUrlResList();

            Map<Long, String> fileUrlMap = viewUrlResList.stream()
                    .collect(Collectors.toMap(
                            GenerateFileViewUrlRes.ViewUrlRes::getFileId,
                            GenerateFileViewUrlRes.ViewUrlRes::getFileUrl
                    ));

            fileResList = savedFileList.stream()
                    .map(file -> GetCustomerRes.CustomerFile.builder()
                            .fileId(file.getId())
                            .customerId(file.getCustomer().getId())
                            .fileKey(file.getFileKey())
                            .category(file.getCategory())
                            .originalFileName(file.getOriginalFileName())
                            .extension(file.getExtension())
                            .contentType(file.getContentType())
                            .size(file.getSize())
                            .fileUrl(fileUrlMap.get(file.getId()))
                            .build())
                    .toList();
        }

        return GetCustomerRes.builder()
                .companyName(customer.getCompanyName())
                .representative(customer.getRepresentative())
                .businessNumber(customer.getBusinessNumber())
                .businessType(customer.getBusinessType())
                .businessItem(customer.getBusinessItem())
                .businessAddress(customer.getBusinessAddress())
                .managerName(customer.getManagerName())
                .companyPhone(customer.getCompanyPhone())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .powerPlannerId(customer.getPowerPlannerId())
                .powerPlannerPassword(cryptoUtil.decrypt(customer.getPowerPlannerPassword()))
                .buildingType(customer.getBuildingType())
                .isTenantFactory(customer.isTenantFactory())
                .tenantCompanyList(tenantCompanyResList)
                .salesmanId(salesman != null ? salesman.getId() : null)
                .salesmanName(salesman != null ? salesman.getUsername() : null)
                .salesmanCommissionRate(salesman != null ? salesman.getSalesmanProfile().getCommissionRate() : null)
                .salesmanPhoneNumber(salesman != null ? salesman.getBasicProfile().getPhone() : null)
                .salesmanEmail(salesman != null ? salesman.getBasicProfile().getEmail() : null)
                .engineerId(engineer != null ? engineer.getId() : null)
                .engineerName(engineer != null ? engineer.getUsername() : null)
                .engineerPhoneNumber(engineer != null ? engineer.getBasicProfile().getPhone() : null)
                .engineerEmail(engineer != null ? engineer.getBasicProfile().getEmail() : null)
                .projectCost(customer.getProjectCost())
                .electricitySavingRate(customer.getElectricitySavingRate())
                .subsidy(customer.getSubsidy())
                .projectPeriod(customer.getProjectPeriod())
                .memo(customer.getMemo())
                .progressStatus(customer.getProgressStatus())
                .customerFileList(fileResList)
                .build();
    }

    @Transactional(readOnly = true)
    public CheckCompanyNameRes checkCompanyName(String companyName) {
        Customer customer = customerRepository.findByCompanyName(companyName);
        if (customer == null) {
            return CheckCompanyNameRes.builder()
                    .possible(true)
                    .salesmanName(null)
                    .salesmanEmail(null)
                    .salesmanPhoneNumber(null)
                    .build();
        }

        User salesman = customer.getSalesmanId();

        return CheckCompanyNameRes.builder()
                .possible(false)
                .salesmanName(salesman!= null ? salesman.getBasicProfile().getName() : null)
                .salesmanPhoneNumber(salesman!= null ? salesman.getBasicProfile().getPhone() : null)
                .salesmanEmail(salesman!= null ? salesman.getBasicProfile().getEmail() : null)
                .build();
    }

    @Transactional
    public GetCustomerRes updateCustomer(Long customerId, UpdateCustomerReq req) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("의뢰서가 존재하지 않습니다."));

        // 영업사원 & 엔지니어 조회
        User salesman = null;
        if (req.getSalesmanId() != null) {
            salesman = userRepo.findById(req.getSalesmanId()).orElseThrow(() -> new EntityNotFoundException("영업사원이 존재하지 않습니다."));
            customer.setSalesmanId(salesman);
        }

        User engineer = null;
        if (req.getEngineerId() != null) {
            engineer = userRepo.findById(req.getEngineerId()).orElseThrow(() -> new EntityNotFoundException("기술자가 존재하지 않습니다."));
            customer.setEngineerId(engineer);
        }

        // 단순 필드 업데이트
        if (req.getCompanyName() != null) customer.setCompanyName(req.getCompanyName());
        if (req.getRepresentative() != null) customer.setRepresentative(req.getRepresentative());
        if (req.getBusinessNumber() != null) customer.setBusinessNumber(req.getBusinessNumber());
        if (req.getBusinessType() != null) customer.setBusinessType(req.getBusinessType());
        if (req.getBusinessItem() != null) customer.setBusinessItem(req.getBusinessItem());
        if (req.getBusinessAddress() != null) customer.setBusinessAddress(req.getBusinessAddress());
        if (req.getManagerName() != null) customer.setManagerName(req.getManagerName());
        if (req.getCompanyPhone() != null) customer.setCompanyPhone(req.getCompanyPhone());
        if (req.getEmail() != null) customer.setEmail(req.getEmail());
        if (req.getPhoneNumber() != null) customer.setPhoneNumber(req.getPhoneNumber());
        if (req.getPowerPlannerId() != null) customer.setPowerPlannerId(req.getPowerPlannerId());
        if (req.getPowerPlannerPassword() != null) {
            customer.setPowerPlannerPassword(cryptoUtil.encrypt(req.getPowerPlannerPassword()));
        }
        if (req.getBuildingType() != null) customer.setBuildingType(req.getBuildingType());
        if (req.getIsTenantFactory() != null) customer.setTenantFactory(req.getIsTenantFactory());
        if (req.getProjectCost() != null) customer.setProjectCost(req.getProjectCost());
        if (req.getElectricitySavingRate() != null) customer.setElectricitySavingRate(req.getElectricitySavingRate());
        if (req.getSubsidy() != null) customer.setSubsidy(req.getSubsidy());
        if (req.getProjectPeriod() != null) customer.setProjectPeriod(req.getProjectPeriod());
        if (req.getMemo() != null) customer.setMemo(req.getMemo());
        if (req.getProgressStatus() != null) customer.setProgressStatus(req.getProgressStatus());

        // 신규 임대차 공장 저장
        if (!req.getNewTenantCompanyList().isEmpty()) {
            List<CustomerTenantCompany> newTenantCompany = req.getNewTenantCompanyList().stream()
                    .map(t -> CustomerTenantCompany.builder()
                            .customer(customer)
                            .tenantCompanyName(t.getTenantCompanyName())
                            .januaryElectricUsage(t.getJanuaryElectricUsage())
                            .augustElectricUsage(t.getAugustElectricUsage())
                            .build()).toList();
            customerTenantCompanyRepository.saveAll(newTenantCompany);
        }

        // 기존 임대차 공장 삭제
        if (!req.getDeleteTenantCompanyList().isEmpty()) {
            List<CustomerTenantCompany> tenantCompanyList = customerTenantCompanyRepository.findAllById(req.getDeleteTenantCompanyList());
            customerTenantCompanyRepository.deleteAll(tenantCompanyList);
        }

        // 신규 파일 저장
        if (!req.getNewAttachmentFileList().isEmpty()) {
            // db 저장
            List<File> newFile = req.getNewAttachmentFileList().stream()
                    .map(f -> File.builder()
                            .customer(customer)
                            .fileKey(f.getFileKey())
                            .category(f.getCategory())
                            .originalFileName(f.getOriginalFileName())
                            .extension(f.getExtension())
                            .contentType(f.getContentType())
                            .size(f.getSize())
                            .build()).toList();
            fileRepository.saveAll(newFile);
        }

        // 기존 파일 삭제
        if (!req.getDeleteAttachmentFileList().isEmpty()) {
            List<File> fileList = fileRepository.findAllById(req.getDeleteAttachmentFileList());
            // DB, S3 삭제처리
            for (File file : fileList) {
                fileService.deleteFile(file.getFileKey());
            }
        }

        return getCustomer(customerId);
    }

    @Transactional
    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("의뢰서가 존재하지 않습니다."));

        // 수용가 삭제
        if (customer != null) {
            // 임대차 업체 삭제
            List<CustomerTenantCompany> savedTenantCompany = customerTenantCompanyRepository.findAllByCustomer(customer);
            customerTenantCompanyRepository.deleteAll(savedTenantCompany);

            // 파일 삭제
            List<File> savedFileList = fileRepository.findAllByCustomer(customer);
            for (File item : savedFileList) {
                // db, s3 삭제
                fileService.deleteFile(item.getFileKey());
            }

            customerRepository.delete(customer);
        }
    }

    @Transactional
    public void updateCustomerTenantCompany(Long tenantCompanyId, UpdateCustomerTenantCompanyReq req) {
        CustomerTenantCompany tenantCompany = customerTenantCompanyRepository.findById(tenantCompanyId).orElseThrow(() -> new EntityNotFoundException("임대차 업체가 존재하지 않습니다."));

        if (req.getTenantCompanyName() != null) tenantCompany.setTenantCompanyName(req.getTenantCompanyName());
        if (req.getJanuaryElectricUsage() != null) tenantCompany.setJanuaryElectricUsage(req.getJanuaryElectricUsage());
        if (req.getAugustElectricUsage() != null) tenantCompany.setAugustElectricUsage(req.getAugustElectricUsage());
    }

    @Transactional
    public void deleteCustomerTenantCompany(Long tenantCompanyId) {
        CustomerTenantCompany tenantCompany = customerTenantCompanyRepository.findById(tenantCompanyId).orElseThrow(() -> new EntityNotFoundException("임대차 업체가 존재하지 않습니다."));

        customerTenantCompanyRepository.delete(tenantCompany);
    }

    public GetCustomerCodeRes getCustomerCode() {
        // 빌딩유형 리스트
        List<GetCustomerCodeRes.BuildingCode> buildingCodeList = Arrays.stream(BuildingType.values())
                .map(type -> GetCustomerCodeRes.BuildingCode.builder()
                        .code(type.name())
                        .label(type.getLabel())
                        .build())
                .toList();

        // 진행상황 리스트
        List<GetCustomerCodeRes.ProgressCode> progressCodeList = Arrays.stream(ProgressStatus.values())
                .map(status -> new GetCustomerCodeRes.ProgressCode(
                        status.name(),
                        status.getLabel()
                ))
                .toList();

        return GetCustomerCodeRes.builder()
                .buildingCodeList(buildingCodeList)
                .progressCodeList(progressCodeList)
                .build();
    }
}
