package com.hanjeonerp.backend.module.user.api.dto.register;

import com.hanjeonerp.backend.module.user.domain.vo.SalesmanProfile;
import com.hanjeonerp.backend.module.user.domain.vo.SettlementMethod;
import com.hanjeonerp.backend.module.user.domain.vo.UserBasicProfile;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesmanReq {

    // 계정 정보
    private String username;
    private String password;

    // 기본 프로필
    @NotNull private String name;
    @NotNull private String phone;
    @NotNull private String email;
    @NotNull private String address;

    // 수수료 및 사업자 정보
    @NotNull private Double commissionRate;
    @NotNull private SettlementMethod settlementMethod;
    @NotNull private String bankName;
    @NotNull private String bankAccount;

    private String businessNumber;
    private String representative;
    private String businessItem;
    private String businessType;
    private String businessAddress;

    // UserBasicProfile 생성
    public UserBasicProfile toBasicProfile() {
        return new UserBasicProfile(name, phone, email, address);
    }

    // SalesmanProfile 생성
    public SalesmanProfile toSalesmanProfile() {
        return new SalesmanProfile(
                commissionRate,
                settlementMethod,
                bankName,
                bankAccount,
                businessNumber,
                representative,
                businessItem,
                businessType,
                businessAddress
        );
    }
}
