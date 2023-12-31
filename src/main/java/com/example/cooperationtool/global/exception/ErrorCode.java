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
    NOT_EXIST_USER(HttpStatus.BAD_REQUEST, "1005", "유저가 존재하지 않습니다."),
    MODIFY_PROFILE_FAILED(HttpStatus.BAD_REQUEST, "1006", "유저 정보 수정에 실패했습니다."),
    NOT_ADMIN(HttpStatus.BAD_REQUEST, "1007", "관리자가 아닙니다."),
    NOT_AUTHORIZATION(HttpStatus.BAD_REQUEST, "1008", "인증되지 않은 사용자입니다."),

    // board (2000)
    NOT_FOUND_BOARD(HttpStatus.BAD_REQUEST, "2001", "요청하신 보드가 존재하지 않습니다."),
    NOT_MATCH_USER(HttpStatus.BAD_REQUEST, "2002", "작성자만 수정 및 삭제를 할 수 있습니다."),
    ILLEGAL_BOARD_TYPE(HttpStatus.BAD_REQUEST, "2003", "해당되지 않는 보드 입니다."),
    NOT_INVITE_YOURSELF(HttpStatus.BAD_REQUEST, "2004", "자신을 초대할 수 없습니다."),
    ALREADY_INVITE_USER(HttpStatus.BAD_REQUEST, "2005", "이미 초대한 유저입니다."),
    NOT_MATCH_BOARD(HttpStatus.BAD_REQUEST, "2006", "보드 생성자만 초대할 수 있습니다."),
    NOT_INVITE_USER(HttpStatus.BAD_REQUEST, "2007", "아직 보드에 초대되지 않았습니다."),
    NOT_INVITE_MYSELF(HttpStatus.BAD_REQUEST, "2009", "보드 생성자 본인은 초대를 취소할 수 없습니다."),

    // column (3000)
    NOT_FOUND_COLUMN(HttpStatus.BAD_REQUEST, "3000", "해당하는 컬럼을 찾을 수 없습니다"),
    NOT_IN_COLUMN(HttpStatus.BAD_REQUEST, "3001", "컬럼이 현재 보드에 속해있지 않습니다"),

    // card (4000)
    NOT_FOUND_CARD(HttpStatus.BAD_REQUEST, "4000", "카드 조회 실패"),

    // comment (5000)
    NOT_FOUND_COMMENT(HttpStatus.BAD_REQUEST, "5000", "해당 댓글은 존재하지 않습니다."),
    NOT_COMMENT_CONTENT(HttpStatus.BAD_REQUEST, "5001", "내용을 입력해주세요."),

    // todos(6000)
    NOT_FOUND_TODO(HttpStatus.BAD_REQUEST, "6000" ,"해당 할일을 찾을 수 없습니다.")

    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}