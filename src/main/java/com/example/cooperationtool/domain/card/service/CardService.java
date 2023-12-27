package com.example.cooperationtool.domain.card.service;

import com.example.cooperationtool.domain.card.dto.CardRequestDto;
import com.example.cooperationtool.domain.card.dto.CardResponseDto;
import com.example.cooperationtool.domain.card.entity.Card;
import com.example.cooperationtool.domain.card.exception.NotFoundCardException;
import com.example.cooperationtool.domain.card.repository.CardRepository;
import com.example.cooperationtool.global.dto.response.RootResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public RootResponseDto createCard(CardRequestDto cardRequestDto) {
        Card card = cardRepository.save(Card.builder()
            .title(cardRequestDto.getTitle())
            .build());

        return new RootResponseDto("200","생성 완료",card);
    }

    public List<CardResponseDto> getCards() {
        List<Card> cardList = cardRepository.findAll();
        return cardList.stream()
            .map(CardResponseDto::of)
            .collect(Collectors.toList());
    }

    public RootResponseDto getCard(Long id) {
        Card card = cardRepository.findById(id).orElseThrow(
            () -> new NotFoundCardException("cardId",id.toString(),"Card를 찾을 수 없습니다.")
        );
        return new RootResponseDto("200","조회 완료", card);
    }
}
