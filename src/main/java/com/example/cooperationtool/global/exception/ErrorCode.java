package com.example.cooperationtool.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // user (1000)
    NOT_EXIST_USER(HttpStatus.BAD_REQUEST, "1000", "존재하지 않는 유저입니다."),
    
    // board (2000)

    // column (3000)

    // card (4000)
    // comment (5000)
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}