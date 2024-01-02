package com.example.cooperationtool.domain.todo.dto;

import com.example.cooperationtool.domain.todo.entity.Todo;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class TodoResponseDto {

    private String content;
    private boolean complete;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String nickname;

    public TodoResponseDto(Todo todo) {
        this.content = todo.getContent();
        this.complete = todo.isComplete();
        this.createdAt = todo.getCreatedAt();
        this.modifiedAt = todo.getModifiedAt();
        this.nickname = todo.getNickname();
    }

    public static TodoResponseDto of(Todo todo) {
        return TodoResponseDto.builder()
            .content(todo.getContent())
            .nickname(todo.getNickname())
            .complete(todo.isComplete())
            .createdAt(todo.getCreatedAt())
            .modifiedAt(todo.getModifiedAt())
            .build();
    }
}
