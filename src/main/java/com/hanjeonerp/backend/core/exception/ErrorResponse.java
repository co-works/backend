package com.hanjeonerp.backend.core.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * ErrorResponse는 API 응답에서 오류 정보를 나타내는 클래스
 * 이 클래스는 HTTP 상태 코드, 오류 코드, 오류 메시지를 포함
 * 주로 예외 처리 시 클라이언트에게 오류 정보를 전달하는 데 사용
 */

@Builder
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final int status;
    private final String code;
    private final String message;

    public static ErrorResponse from(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }

    public static ErrorResponse from(BaseException exception) {
        ErrorCode code = exception.getErrorCode();
        return ErrorResponse.builder()
                .status(code.getStatus())
                .code(code.getCode())
                .message(exception.getMessage())
                .build();
    }



}
