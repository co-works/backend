package com.hanjeonerp.backend.core.exception;


/**
 * InternalServerException은 서버 내부에서 발생하는 예외 (uncaught exception)
 * 이 예외는 500 Internal Server Error HTTP 상태 코드와 함께 사용
 * 주로 서버의 예기치 않은 오류나 처리 실패 시 발생
 */
public class InternalServerException extends BaseException {

    public InternalServerException(String message) {
        super(ErrorCode.INTERNAL_SERVER_ERROR, message);
    }

    public InternalServerException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public InternalServerException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InternalServerException(String message, Throwable cause) {
        super(ErrorCode.INTERNAL_SERVER_ERROR, message);
        initCause(cause);
    }
}
