package com.example.cooperationtool.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // user (1000)
    SIGNUP_FAIL(HttpStatus.BAD_REQUEST, "1000", "회원가입에 실패했습니다."),
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "1001", "중복된 사용자 이름입니다."),
    WRONG_ADMIN_CODE(HttpStatus.BAD_REQUEST, "1002", "관리자 암호가 틀려 등록이 불가능합니다."),
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "1003", "로그인에 실패했습니다."),
    TOKEN_ERROR(HttpStatus.BAD_REQUEST, "1004", "토큰이 틀립니다."),

    // board (2000)

    // column (3000)

    // card (4000)
    NOT_FOUND_CARD(HttpStatus.BAD_REQUEST, "4000","Card 생성 실패."),
    NOT_FOUND_WORKER(HttpStatus.NOT_FOUND,"4000","존재하지 않는 Worker입니다..");


    // comment (5000)

    private final HttpStatus status;
    private final String code;
    private final String message;
}