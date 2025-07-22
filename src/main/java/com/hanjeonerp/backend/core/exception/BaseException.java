package com.hanjeonerp.backend.core.exception;

import lombok.Getter;

/**
 * BaseException은 모든 사용자 정의 예외의 기본 클래스
 * 이 클래스를 상속받아 구체적인 예외를 정의
 * 예외 코드와 메시지를 포함하며, RuntimeException을 상속
 */

@Getter
public class BaseException extends RuntimeException {
    private final ErrorCode errorCode;

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BaseException(ErrorCode errorCode, String customMessage) {
        super(customMessage);// 커스텀 메시지
        this.errorCode = errorCode;
    }

}
