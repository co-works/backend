package com.hanjeonerp.backend.module.engineer.domain.entity;

import com.hanjeonerp.backend.module.engineer.domain.vo.EngineerProfile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Engineer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Embedded
    private EngineerProfile engineerProfile; // 엔지니어 프로필 정보

    public Engineer(EngineerProfile engineerProfile) {
        this.engineerProfile = engineerProfile;
    }

    private boolean isDeleted = false;

    //엔지니어 생성자
    public static Engineer createEngineer(EngineerProfile engineerProfile) {
        return new Engineer(engineerProfile);
    }

    public void changeProfile(EngineerProfile engineerProfile) {
        this.engineerProfile = engineerProfile;
    }

    //soft delete
    public void delete() {
        this.isDeleted = true;
    }
}
