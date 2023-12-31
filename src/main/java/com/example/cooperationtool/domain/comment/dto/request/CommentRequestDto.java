package com.example.cooperationtool.domain.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class CommentRequestDto {
        private String content;
        private Long cardId;
}
