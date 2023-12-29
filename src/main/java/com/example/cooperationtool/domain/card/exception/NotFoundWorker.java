package com.example.cooperationtool.domain.card.exception;

import com.example.cooperationtool.global.exception.ErrorCode;

public class NotFoundWorker extends RuntimeException{

    public NotFoundWorker(String userId, String value, String reason) {
        super(
            ErrorCode.NOT_FOUND_WORKER.getMessage()
        );
    }

}
