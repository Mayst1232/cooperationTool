package com.example.cooperationtool.domain.card.dto;

import com.example.cooperationtool.domain.card.entity.Card;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@NotBlank
public class CardResponseDto extends Card {

    private Long id;
    private String title;
    private LocalDateTime createdAt;

    public static CardResponseDto of(Card card) {
        return CardResponseDto.builder()
            .id(card.getId())
            .title(card.getTitle())
            .createdAt(card.getCreatedAt())
            .build();
    }
}
