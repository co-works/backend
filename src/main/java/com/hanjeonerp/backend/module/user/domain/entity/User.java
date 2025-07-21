package com.hanjeonerp.backend.module.user.domain.entity;

import com.hanjeonerp.backend.core.common.BaseTimeEntity;
import com.hanjeonerp.backend.module.user.domain.vo.Role;
import com.hanjeonerp.backend.module.user.domain.vo.SalesmanProfile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String username; // 사용자 이름
    private String password; // 사용자 비밀번호

    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded
    private SalesmanProfile salesmanProfile; // 영업사원 전용 프로필  (관지라는 null)

    // 영업사원 생성
    public static User createSalesman(String username, String password, SalesmanProfile profile) {
        return new User(username, password, Role.SALESMAN, profile);
    }

    //관리자(내부 테스트용)
    public static User createAdmin(String username, String password) {
        return new User(username, password, Role.ADMIN, null);
    }

    //도메인 생성자 (역할 제약 검증 포함)
    private User(String username, String password, Role role, SalesmanProfile profile) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.salesmanProfile = profile;


        //관리자는 영업사원 프로필을 가지지 못하도록 검증
        if (role ==Role.ADMIN&& profile != null) {
            throw new IllegalArgumentException("관리자는 영업사원 프로필을 가질 수 없습니다.");
        }
    }
    //관리자 권한 체크
    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }

    //영업사원 권한 체크
    public boolean isSalesman() {
        return this.role == Role.SALESMAN;
    }
}
