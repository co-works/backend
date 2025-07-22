package com.hanjeonerp.backend.core.common;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private final int status;   // HTTP 상태 코드
    private final String message; // 응답 메시지 (성공/실패 안내)
    private final T data;       // 실제 응답 데이터

    // 성공 응답 생성 (데이터 포함)
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "성공", data);
    }


    // 성공 응답 생성 (데이터 없음)
    public static ApiResponse<Void> success() {
        return new ApiResponse<>(200, "성공", null);
    }

    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
