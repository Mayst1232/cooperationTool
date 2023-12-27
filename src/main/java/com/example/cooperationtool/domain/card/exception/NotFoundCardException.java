package com.example.cooperationtool.domain.card.exception;

import com.example.cooperationtool.global.exception.ErrorCode;

public class NotFoundCardException extends RuntimeException{

    public NotFoundCardException(String id, String value, String reason) {
        super(
            ErrorCode.NOT_FOUND_CARD.getMessage()
        );
    }
}
