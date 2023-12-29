package com.example.cooperationtool.domain.column.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ColumnRequestDto {

    @NotBlank
    private String name;
}
