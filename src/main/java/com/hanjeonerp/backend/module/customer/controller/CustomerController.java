package com.hanjeonerp.backend.module.customer.controller;

import com.hanjeonerp.backend.core.common.ApiResponse;
import com.hanjeonerp.backend.module.customer.dto.req.GenerateCustomerReq;
import com.hanjeonerp.backend.module.customer.dto.req.UpdateCustomerReq;
import com.hanjeonerp.backend.module.customer.dto.res.GetCustomerCodeRes;
import com.hanjeonerp.backend.module.customer.dto.res.GetCustomerRes;
import com.hanjeonerp.backend.module.customer.dto.res.UpdateCustomerRes;
import com.hanjeonerp.backend.module.customer.dto.res.GenerateCustomerRes;
import com.hanjeonerp.backend.module.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Tag(name = "Customer", description = "수용가 관련 컨트롤러")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    @Operation(summary = "타당성 검토 의뢰 or 수용가 추가(고객,업체 사용)")
    public ApiResponse<GenerateCustomerRes> generateCustomer(@RequestBody GenerateCustomerReq req) {
        return ApiResponse.success(customerService.generateCustomer(req));
    }

    @GetMapping("/{customerId}")
    @Operation(summary = "타당성 검토 의뢰 or 수용가 추가 조회")
    public ApiResponse<GetCustomerRes> getCustomer(@PathVariable Long customerId) {
        return ApiResponse.success(customerService.getCustomer(customerId));
    }

    @GetMapping("/check/company-name")
    @Operation(summary = "업체명 중복체크")
    public ApiResponse<String> checkCompanyName(String companyName) {
        return ApiResponse.success(customerService.checkCompanyName(companyName));
    }

    @PatchMapping("/{customerId}")
    @Operation(summary = "타당성 검토 의뢰 or 수용가 수정 및 삭제")
    public ApiResponse<UpdateCustomerRes> updateCustomer(@PathVariable Long customerId,
                                                         @RequestBody UpdateCustomerReq req) {
        return ApiResponse.success(customerService.updateCustomer(customerId, req));
    }

    @GetMapping("/code")
    @Operation(summary = "수용가에서 사용하는 코드 모음(건물 유형, 진행 상태)")
    public ApiResponse<GetCustomerCodeRes> getCustomerCode() {
        return ApiResponse.success(customerService.getCustomerCode());
    }

}
