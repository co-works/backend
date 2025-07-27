package com.hanjeonerp.backend.module.user.domain.service;

import com.hanjeonerp.backend.module.user.domain.entity.User;
import com.hanjeonerp.backend.module.user.domain.vo.SalesmanProfile;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    // 영업사원 생성 로직
    public User createSalesMan(String username, String encodedPassword, SalesmanProfile salesmanProfile) {


        return User.createSalesman(username, encodedPassword, salesmanProfile);
    }


}

