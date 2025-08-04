package com.hanjeonerp.backend.module.customer.service;

import com.hanjeonerp.backend.core.util.CryptoUtil;
import com.hanjeonerp.backend.module.customer.domain.entity.Customer;
import com.hanjeonerp.backend.module.customer.domain.repo.CustomerRepository;
import com.hanjeonerp.backend.module.customer.domain.vo.BuildingType;
import com.hanjeonerp.backend.module.customer.domain.vo.ProgressStatus;
import com.hanjeonerp.backend.module.customer.dto.req.GenerateCustomerReq;
import com.hanjeonerp.backend.module.customer.dto.req.UpdateCustomerReq;
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
    private final FileRepository fileRepository;
    private final UserRepo userRepo;

    private final FileService fileService;

    @Transactional
    public GenerateCustomerRes generateCustomer(GenerateCustomerReq req) {
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
                .januaryElectricUsage(req.getJanuaryElectricUsage())
                .augustElectricUsage(req.getAugustElectricUsage())
                .salesmanId(salesman)
                .engineerId(engineer)
                .projectCost(req.getProjectCost())
                .electricitySavingRate(req.getElectricitySavingRate())
                .subsidy(req.getSubsidy())
                .projectPeriod(req.getProjectPeriod())
                .progressStatus(req.getProgressStatus())
                .build();
        customerRepository.save(customer);

        // 파일 저장
        List<File> fileList = req.getAttachmentFileList();
        if (!fileList.isEmpty()) {
            for (File item : fileList) {
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

        // 저장된 파일 조회
        List<File> savedFileList = fileRepository.findAllByCustomer(customer);
        // presigned URL 포함된 파일 응답 DTO 리스트 생성
        List<GenerateCustomerRes.CustomerFile> fileResList = new ArrayList<>();
        if (!savedFileList.isEmpty()) {
            // 1. ViewUrlReq 리스트 생성
            List<GenerateFileViewUrlReq.ViewUrlReq> viewUrlReqList = savedFileList.stream()
                    .map(file -> new GenerateFileViewUrlReq.ViewUrlReq(file.getId(), file.getFileKey()))
                    .toList();

            // 2. S3 조회용 URL 한 번에 요청
            GenerateFileViewUrlReq viewUrlReq = new GenerateFileViewUrlReq(viewUrlReqList);
            List<GenerateFileViewUrlRes.ViewUrlRes> viewUrlResList = fileService.generateFileViewURL(viewUrlReq).getViewUrlResList();

            // 3. fileId → fileUrl 매핑용 Map 생성
            Map<Long, String> fileUrlMap = viewUrlResList.stream()
                    .collect(Collectors.toMap(
                            GenerateFileViewUrlRes.ViewUrlRes::getFileId,
                            GenerateFileViewUrlRes.ViewUrlRes::getFileUrl
                    ));

            // 4. 최종 응답용 CustomerFile 리스트 생성
            fileResList = savedFileList.stream()
                    .map(file -> GenerateCustomerRes.CustomerFile.builder()
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

        /**
         * engineer 값 변경 필요
         **/
        return GenerateCustomerRes.builder()
                .companyName(customer.getCompanyName())
                .representative(customer.getRepresentative())
                .businessNumber(customer.getBusinessNumber())
                .businessType(customer.getBusinessType())
                .businessItem(customer.getBusinessItem())
                .businessAddress(customer.getBusinessAddress())
                .managerName(customer.getManagerName())
                .companyPhone(customer.getCompanyPhone())
                .email(customer.getEmail())
                .powerPlannerId(customer.getPowerPlannerId())
                .powerPlannerPassword(cryptoUtil.decrypt(customer.getPowerPlannerPassword())) // 복호화해서 전달
                .buildingType(customer.getBuildingType())
                .isTenantFactory(customer.isTenantFactory())
                .januaryElectricUsage(customer.getJanuaryElectricUsage())
                .augustElectricUsage(customer.getAugustElectricUsage())
                .salesmanId(salesman != null ? salesman.getId() : null)
                .salesmanName(salesman != null ? salesman.getSalesmanProfile().getSalesmanName() : null)
                .salesmanCommissionRate(salesman != null ? salesman.getSalesmanProfile().getCommissionRate() : null)
                .salesmanPhoneNumber(salesman != null ? salesman.getSalesmanProfile().getSalesmanPhone() : null)
                .engineerId(null)
                .engineerName(null)
                .engineerPhoneNumber(null)
                .engineerEmail(null)
                .projectCost(customer.getProjectCost())
                .electricitySavingRate(customer.getElectricitySavingRate())
                .subsidy(customer.getSubsidy())
                .projectPeriod(customer.getProjectPeriod())
                .progressStatus(customer.getProgressStatus())
                .customerFileList(fileResList)
                .build();
    }

    @Transactional(readOnly = true)
    public GetCustomerRes getCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("의뢰서가 존재하지 않습니다."));

        if (customer == null) {
            return null;
        }

        User salesman = customer.getSalesmanId();
        User engineer = customer.getEngineerId();

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

        /**
         * engineer 값 변경 필요
         **/
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
                .januaryElectricUsage(customer.getJanuaryElectricUsage())
                .augustElectricUsage(customer.getAugustElectricUsage())
                .salesmanId(salesman != null ? salesman.getId() : null)
                .salesmanName(salesman != null ? salesman.getSalesmanProfile().getSalesmanName() : null)
                .salesmanCommissionRate(salesman != null ? salesman.getSalesmanProfile().getCommissionRate() : null)
                .salesmanPhoneNumber(salesman != null ? salesman.getSalesmanProfile().getSalesmanPhone() : null)
                .salesmanEmail(salesman != null ? salesman.getSalesmanProfile().getSalesmanEmail() : null)
                .engineerId(null)
                .engineerName(null)
                .engineerPhoneNumber(null)
                .engineerEmail(null)
                .projectCost(customer.getProjectCost())
                .electricitySavingRate(customer.getElectricitySavingRate())
                .subsidy(customer.getSubsidy())
                .projectPeriod(customer.getProjectPeriod())
                .progressStatus(customer.getProgressStatus())
                .customerFileList(fileResList)
                .build();
    }

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
                .salesmanName(salesman.getSalesmanProfile().getSalesmanName())
                .salesmanPhoneNumber(salesman.getSalesmanProfile().getSalesmanPhone())
                .salesmanEmail(salesman.getSalesmanProfile().getSalesmanEmail())
                .build();
    }

    @Transactional
    public UpdateCustomerRes updateCustomer(Long customerId, UpdateCustomerReq req) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("의뢰서가 존재하지 않습니다."));

        if (customer == null) {
            return null;
        }

        if (req.getCompanyName() != null) {
            customer.setCompanyName(req.getCompanyName());
        }

        if (req.getRepresentative() != null) {
            customer.setRepresentative(req.getRepresentative());
        }

        if (req.getBusinessNumber() != null) {
            customer.setBusinessAddress(req.getBusinessNumber());
        }

        if (req.getBusinessType() != null) {
            customer.setBusinessType(req.getBusinessType());
        }

        if (req.getBusinessItem() != null) {
            customer.setBusinessItem(req.getBusinessItem());
        }

        if (req.getBusinessAddress() != null) {
            customer.setBusinessAddress(req.getBusinessAddress());
        }

        if (req.getManagerName() != null) {
            customer.setManagerName(req.getManagerName());
        }

        if (req.getCompanyPhone() != null) {
            customer.setCompanyPhone(req.getCompanyPhone());
        }

        if (req.getEmail() != null) {
            customer.setEmail(req.getEmail());
        }

        if (req.getPhoneNumber() != null) {
            customer.setPhoneNumber(req.getPhoneNumber());
        }

        if (req.getPowerPlannerId() != null) {
            customer.setPowerPlannerId(req.getPowerPlannerId());
        }

        if (req.getPowerPlannerPassword() != null) {
            String encodePoserPlannerPassword = cryptoUtil.encrypt(req.getPowerPlannerPassword());
            customer.setPowerPlannerPassword(encodePoserPlannerPassword);
        }

        if (req.getBuildingType() != null) {
            customer.setBuildingType(req.getBuildingType());
        }

        if (req.getIsTenantFactory() != null) {
            customer.setTenantFactory(req.getIsTenantFactory());
        }

        if (req.getJanuaryElectricUsage() != null) {
            customer.setJanuaryElectricUsage(req.getJanuaryElectricUsage());
        }

        if (req.getAugustElectricUsage() != null) {
            customer.setAugustElectricUsage(req.getAugustElectricUsage());
        }

        if (req.getSalesmanId() != null) {
            User salesman = userRepo.findById(req.getSalesmanId()).orElseThrow(() -> new EntityNotFoundException("영업사원이 존재하지 않습니다."));
            customer.setSalesmanId(salesman);
        }

        if (req.getEngineerId() != null) {
            User engineer = userRepo.findById(req.getSalesmanId()).orElseThrow(() -> new EntityNotFoundException("기술자가 존재하지 않습니다."));
            customer.setEngineerId(engineer);
        }

        if (req.getProjectCost() != null) {
            customer.setProjectCost(req.getProjectCost());
        }

        if (req.getElectricitySavingRate() != null) {
            customer.setElectricitySavingRate(req.getElectricitySavingRate());
        }

        if (req.getSubsidy() != null) {
            customer.setSubsidy(req.getSubsidy());
        }

        if (req.getProjectPeriod() != null) {
            customer.setProjectPeriod(req.getProjectPeriod());
        }

        if (req.getProgressStatus() != null) {
            customer.setProgressStatus(req.getProgressStatus());
        }

        if (!req.getNewAttachmentFileList().isEmpty()) {
            // db 저장
            List<File> newFile = req.getNewAttachmentFileList().stream()
                    .map(f -> File.builder()
                            .fileKey(f.getFileKey())
                            .category(f.getCategory())
                            .originalFileName(f.getOriginalFileName())
                            .extension(f.getExtension())
                            .contentType(f.getContentType())
                            .size(f.getSize())
                            .build()).toList();
            fileRepository.saveAll(newFile);
        }

        if (!req.getDeleteAttachmentFileList().isEmpty()) {
            List<File> fileList = fileRepository.findAllById(req.getDeleteAttachmentFileList());
            // S3 삭제처리
            for (File file : fileList) {
                fileService.deleteFile(file.getFileKey());
            }

            // DB 삭제처리
            fileRepository.deleteAll(fileList);
        }

        return UpdateCustomerRes.builder()
                .build();
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
