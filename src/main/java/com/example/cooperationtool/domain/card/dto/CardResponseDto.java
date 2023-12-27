package com.example.cooperationtool.domain.card.dto;

import com.example.cooperationtool.domain.card.entity.Card;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class CardResponseDto{

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
