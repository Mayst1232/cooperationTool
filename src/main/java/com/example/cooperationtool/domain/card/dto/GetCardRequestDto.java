package com.example.cooperationtool.domain.card.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GetCardRequestDto {

    Long boardId;

    @Builder
    public GetCardRequestDto(Long boardId) {
        this.boardId = boardId;
    }
}
