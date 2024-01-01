package com.example.cooperationtool.domain.card.dto;

import com.example.cooperationtool.domain.card.entity.Card;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class CardResponseDto {

    private Long cardId;

    private String title;

    @JsonInclude
    private LocalDateTime createdAt;

    @JsonInclude(Include.NON_NULL)
    private LocalDateTime modifiedAt;

    @JsonInclude(Include.NON_NULL)
    private Integer dday;

    @JsonInclude(Include.NON_NULL)
    private LocalDateTime dueDate;


    public CardResponseDto(Card card) {
        this.cardId = card.getId();
        this.title = card.getTitle();
        this.createdAt = card.getCreatedAt();
        this.modifiedAt = card.getModifiedAt();
        this.dday = card.getDday();
        this.dueDate = card.getDueDate();
    }

    public static CardResponseDto of(Card card) {
        return CardResponseDto.builder()
            .cardId(card.getId())
            .title(card.getTitle())
            .createdAt(card.getCreatedAt())
            .modifiedAt(card.getModifiedAt())
            .dday(card.getDday())
            .dueDate(card.getDueDate())
            .build();
    }
}
