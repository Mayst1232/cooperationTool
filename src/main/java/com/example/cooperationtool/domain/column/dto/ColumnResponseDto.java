package com.example.cooperationtool.domain.column.dto;

import com.example.cooperationtool.domain.column.entity.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ColumnResponseDto {
    private Long id;
    private String name;

    @Builder
    public ColumnResponseDto(Column column){
        this.id = column.getId();
        this.name = column.getName();
    }
}
