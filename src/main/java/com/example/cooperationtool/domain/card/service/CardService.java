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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public RootResponseDto createCard(CardRequestDto cardRequestDto) {
        Card card = cardRepository.save(Card.builder().title(cardRequestDto.getTitle()).build());
        return new RootResponseDto("200","생성 완료",card);
    }

    public List<CardResponseDto> getCards() {
        List<Card> cardList = cardRepository.findAll();
        return cardList.stream()
            .map(CardResponseDto::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RootResponseDto getCard(Long cardId) {
        Card card = extracted(cardId);
        return new RootResponseDto("200","조회 완료", card);
    }

    @Transactional
    public RootResponseDto modifyCard(Long cardId, CardRequestDto requestDto) {
        Card card = extracted(cardId);
        if(card != null){
            Card modifyCard = cardRepository.save(Card.builder().title(requestDto.getTitle()).build());
            return new RootResponseDto("200","수정 성공",modifyCard);
        }else{
            throw new NotFoundCardException("cardId", cardId.toString(), "Card를 찾을 수 없습니다.");
        }
    }

    @Transactional
    public void deleteCard(Long cardId) {
        Card card = extracted(cardId);
        if(card != null){
            cardRepository.deleteById(cardId);
            ResponseEntity.ok().body("성공적으로 삭제되었습니다.");
        }else {
            throw new NotFoundCardException("cardId", cardId.toString(), "Card를 찾을 수 없습니다.");
        }
    }

    private Card extracted(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(
            () -> new NotFoundCardException("cardId", cardId.toString(),"Card를 찾을 수 없습니다.")
        );
    }

}
