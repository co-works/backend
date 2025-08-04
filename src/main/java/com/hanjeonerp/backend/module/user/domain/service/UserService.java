package com.hanjeonerp.backend.module.user.domain.service;

import com.hanjeonerp.backend.module.user.domain.entity.User;
import com.hanjeonerp.backend.module.user.domain.vo.SalesmanProfile;
import com.hanjeonerp.backend.module.user.domain.vo.UserBasicProfile;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    // 영업사원 생성 로직
    public User createSalesMan(String username, String password, UserBasicProfile basicProfile, SalesmanProfile profile) {
        return User.createSalesman(username, password, basicProfile, profile);
    }


    // 기술자 생성 로직
    public User createEngineer(String username, String encodedPassword, UserBasicProfile engineerProfile) {

        return User.createEngineer(username, encodedPassword, engineerProfile);
    }


}

