package com.example.cooperationtool.domain.column.dto;

import com.example.cooperationtool.domain.column.entity.Columns;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ColumnResponseDto {
    private String name;

    @Builder
    public ColumnResponseDto(Columns columns){
        this.name = columns.getName();
    }
}
