package com.example.cooperationtool.domain.card.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class CardRequestDto {

    @NotBlank
    private String title;

}
