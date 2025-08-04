package com.hanjeonerp.backend.module.customer.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckCompanyNameRes {
    private Boolean possible;
    private String salesmanName;
    private String salesmanPhoneNumber;
    private String salesmanEmail;
}
