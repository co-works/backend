package com.hanjeonerp.backend.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


/**
 * ErrorCode는 애플리케이션에서 발생할 수 있는 다양한 오류를 정의하는 enum
 * 각 오류는 HTTP 상태 코드, 고유 코드, 그리고 사용자에게 보여줄 메시지를 포함
 * 이 enum을 사용하여 일관된 오류 처리를 구현
 */

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 400 BAD REQUEST
    BAD_REQUEST(400, "C000", "잘못된 요청입니다."),
    INVALID_INPUT(400, "C001", "입력값이 올바르지 않습니다."),
    DUPLICATE_USERNAME(400, "U001", "이미 존재하는 아이디입니다."),
    USER_NOT_FOUND(400, "U002", "사용자를 찾을 수 없습니다."),
    REQUEST_NOT_FOUND(400, "R002", "의뢰서를 찾을 수 없습니다."),


    // 401 UNAUTHORIZED
    UNAUTHORIZED(401, "A001", "인증이 필요합니다."),

    // 403 FORBIDDEN
    FORBIDDEN(403, "A002", "접근 권한이 없습니다."),

    // 404 NOT FOUND
    ENTITY_NOT_FOUND(404, "R001", "리소스를 찾을 수 없습니다."),

    // 409 CONFLICT
    DUPLICATE_RESOURCE(409, "C003", "이미 존재하는 리소스입니다."),
    INVALID_STATE(409, "C004", "처리할 수 없는 상태입니다."),

    // 500 INTERNAL SERVER ERROR
    DATABASE_ERROR(500, "S001", "데이터베이스 오류입니다."),
    INTERNAL_SERVER_ERROR(500, "S002", "서버 오류입니다."),
    SERVICE_UNAVAILABLE(503, "S003", "서비스를 일시적으로 사용할 수 없습니다.");

    private final int status;
    private final String code;
    private final String message;
}
