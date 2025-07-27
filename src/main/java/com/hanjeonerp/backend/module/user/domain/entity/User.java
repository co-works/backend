package com.hanjeonerp.backend.module.user.domain.entity;

import com.hanjeonerp.backend.core.common.BaseTimeEntity;
import com.hanjeonerp.backend.module.user.domain.vo.Role;
import com.hanjeonerp.backend.module.user.domain.vo.SalesmanProfile;
import com.hanjeonerp.backend.module.user.domain.vo.Username;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted = false") //softe delete를 위한 조건절
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

    private boolean isDeleted = false;

    // 영업사원 생성
    public static User createSalesman(String username, String password, SalesmanProfile profile) {
        return new User(username, password, Role.SALESMAN, profile);
    }

    //영업사원 수정
    public void updateSalesmanInfo(String username, String password, SalesmanProfile profile) {
        if (username != null) {
            this.username = username;
        }
        if (password != null) {
            this.password = password;
        }
        if (profile != null) {
            this.salesmanProfile = profile;
        }
    }

    //도메인 생성자 (역할 제약 검증 포함)
    private User(String username, String password, Role role, SalesmanProfile profile) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.salesmanProfile = profile;

    }
    //관리자 권한 체크
    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }

    //영업사원 권한 체크
    public boolean isSalesman() {
        return this.role == Role.SALESMAN;
    }

    //sofe delete
    public void delete() {
        this.isDeleted = true;
    }

    public void changeUsername(String newUsername) {
        this.username = newUsername;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void changeProfile(SalesmanProfile newProfile) {
        this.salesmanProfile = newProfile;
    }

}


