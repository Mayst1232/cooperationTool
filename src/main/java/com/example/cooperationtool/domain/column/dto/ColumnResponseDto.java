package com.example.cooperationtool.domain.column.dto;

import com.example.cooperationtool.domain.column.entity.Columns;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ColumnResponseDto {

    private String title;
    private String name;
    private Long priority;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    public ColumnResponseDto(Columns columns) {
        this.title = columns.getTitle();
        this.name = columns.getUser().getNickname();
        this.priority = columns.getPriority();
        this.createdAt = columns.getCreatedAt();
        this.modifiedAt = columns.getModifiedAt();
    }
}
