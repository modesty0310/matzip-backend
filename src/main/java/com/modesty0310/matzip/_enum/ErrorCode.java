package com.modesty0310.matzip._enum;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    EMAIL_ALREADY_EXISTS("이미 존재하는 이메일입니다.", HttpStatus.CONFLICT),
    INVALID_EMAIL_OR_PASSWORD("이메일 또는 비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_REQUEST("잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    FORBIDDEN("권한이 없습니다.", HttpStatus.FORBIDDEN),
    INVALID_JWT_TOKEN("권한이 없습니다.", HttpStatus.UNAUTHORIZED),
    NOTFOUND_USER("존재하지 않는 유저 입니다.", HttpStatus.NOT_FOUND),
    FAILED_KAKAO_SIGNUP("카카오 회원가입에 실패 하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    KAKAO_SERVER_ERROR("Kakao 서버 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    POST_NOT_FOUND("존재 하지 않는 피드입니다.", HttpStatus.NOT_FOUND),
    OVER_MAX_FILE_COUNT("최대 파일 개수는 5개입니다.", HttpStatus.BAD_REQUEST),
    FAILED_IMAGE_UPLOAD("파일 업로드에 실패 하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    NOTFOUND_POST("존재하지 않는 게시글 입니다.", HttpStatus.NOT_FOUND),
    ;

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
