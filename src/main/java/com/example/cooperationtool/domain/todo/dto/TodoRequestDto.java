package com.example.cooperationtool.domain.todo.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoRequestDto {

    private Long cardId;
    private String content;
    private boolean complete;
    private String nickname;

    @Builder
    public TodoRequestDto(Long cardId, String content, boolean complete, String nickname) {
        this.cardId = cardId;
        this.content = content;
        this.complete = complete;
        this.nickname = nickname;
    }
}
