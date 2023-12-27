package com.example.cooperationtool.domain.board.dto.response;

import com.example.cooperationtool.domain.board.entity.Board;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardResponseDto {

    private String title;
    private String content;

    @Builder
    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
    }

}
