package com.hanjeonerp.backend.module.user.api.dto.update;

import com.hanjeonerp.backend.module.user.domain.vo.SalesmanProfile;
import com.hanjeonerp.backend.module.user.domain.vo.SettlementMethod;
import com.hanjeonerp.backend.module.user.domain.vo.UserBasicProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSalesmanReq {

    // 사용자 계정 정보
    private String username;
    private String password;

    // 영업사원 기본 정보
    private String salesmanName;
    private String salesmanPhone;
    private String salesmanEmail;
    private String salesmanAddress;

    // 수수료 및 정산 정보
    private Double commissionRate;
    private String settlementMethod; // "INVOICE", "WITHHOLDING_TAX"
    private String bankName;
    private String bankAccount;

    // 사업자 정보
    private String businessNumber;
    private String representative;
    private String businessItem;
    private String businessType;
    private String businessAddress;


    public UserBasicProfile toBasicProfile(UserBasicProfile existingProfile) {
        String name = existingProfile.getName();
        if (this.salesmanName != null) name = this.salesmanName;

        String phone = existingProfile.getPhone();
        if (this.salesmanPhone != null) phone = this.salesmanPhone;

        String email = existingProfile.getEmail();
        if (this.salesmanEmail != null) email = this.salesmanEmail;

        String address = existingProfile.getAddress();
        if (this.salesmanAddress != null) address = this.salesmanAddress;

        return new UserBasicProfile(name, phone, email, address);
    }

    public SalesmanProfile toSalesmanProfile(SalesmanProfile existingProfile) {
        Double commission = existingProfile.getCommissionRate();
        if (this.commissionRate != null) commission = this.commissionRate;

        SettlementMethod method = existingProfile.getSettlementMethod();
        if (this.settlementMethod != null) {
            method = SettlementMethod.valueOf(this.settlementMethod);
        }

        String bank = existingProfile.getBankName();
        if (this.bankName != null) bank = this.bankName;

        String account = existingProfile.getBankAccount();
        if (this.bankAccount != null) account = this.bankAccount;

        String businessNumber = existingProfile.getBusinessNumber();
        if (this.businessNumber != null) businessNumber = this.businessNumber;

        String rep = existingProfile.getRepresentative();
        if (this.representative != null) rep = this.representative;

        String item = existingProfile.getBusinessItem();
        if (this.businessItem != null) item = this.businessItem;

        String type = existingProfile.getBusinessType();
        if (this.businessType != null) type = this.businessType;

        String bizAddress = existingProfile.getBusinessAddress();
        if (this.businessAddress != null) bizAddress = this.businessAddress;

        return new SalesmanProfile(
                commission, method, bank, account,
                businessNumber, rep, item, type, bizAddress
        );
    }

}
