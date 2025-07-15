package com.hanjeonerp.backend.core.exception;


/** * BadRequestException은 클라이언트의 요청이 잘못되었을 때 발생
 * 이 예외는 400 Bad Request HTTP 상태 코드와 함께 사용
 * 주로 클라이언트가 잘못된 데이터를 전송했을 때 발생
 */

public class BadRequestException extends BaseException {

    public BadRequestException(String message) {
        super(ErrorCode.BAD_REQUEST, message);
    }

    public BadRequestException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
