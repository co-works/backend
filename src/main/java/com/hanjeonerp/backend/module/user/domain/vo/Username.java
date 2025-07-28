package com.hanjeonerp.backend.module.user.domain.vo;

import com.hanjeonerp.backend.core.exception.BadRequestException;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class Username {

    private String value;

    public Username(String value) {
        if (value == null || value.isEmpty()) {
            throw new BadRequestException("아이디는 공백일 수 없고 필수입니다.");
        }
        this.value = value;
    }

    //팩토리 메서드
    public static Username of(String value) {
        return new Username(value);
    }

}