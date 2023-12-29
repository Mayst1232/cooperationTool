package com.example.cooperationtool.domain.board.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BoardRequestDto {

    @Size(max = 15)
    private String title;

    @Size(max = 20)
    private String content;
}
