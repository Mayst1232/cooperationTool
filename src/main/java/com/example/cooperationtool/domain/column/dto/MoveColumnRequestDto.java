package com.example.cooperationtool.domain.column.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class MoveColumnRequestDto {

    Long destination;
}
