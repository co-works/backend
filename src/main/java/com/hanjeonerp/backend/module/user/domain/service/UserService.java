package com.hanjeonerp.backend.module.user.domain.service;

import com.hanjeonerp.backend.module.user.domain.entity.User;
import com.hanjeonerp.backend.module.user.domain.vo.SalesmanProfile;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    public User createSalesMan(String username, String encodedPassword, SalesmanProfile salesmanProfile) {

        // 영업사원 생성 로직
        return User.createSalesman(username, encodedPassword, salesmanProfile);
    }

}

