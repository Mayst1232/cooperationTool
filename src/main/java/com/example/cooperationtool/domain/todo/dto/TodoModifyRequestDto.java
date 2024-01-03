package com.example.cooperationtool.domain.todo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TodoModifyRequestDto {

    private String content;
    private boolean complete;


    public TodoModifyRequestDto(String content, boolean complete) {
        this.content = content;
        this.complete = complete;
    }
}
