package com.example.cooperationtool.domain.card.service;

import com.example.cooperationtool.domain.card.dto.CardRequestDto;
import com.example.cooperationtool.domain.card.dto.CardResponseDto;
import com.example.cooperationtool.domain.card.entity.Card;
import com.example.cooperationtool.domain.card.exception.NotFoundCardException;
import com.example.cooperationtool.domain.card.exception.NotFoundWorker;
import com.example.cooperationtool.domain.card.repository.CardRepository;
import com.example.cooperationtool.domain.user.entity.User;
import com.example.cooperationtool.global.exception.ErrorCode;
import com.example.cooperationtool.global.exception.ServiceException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public CardResponseDto createCard(CardRequestDto cardRequestDto, User user) {
        Card card = cardRepository.save(Card.builder()
            .user(user)
            .title(cardRequestDto.getTitle())
            .build());

        return CardResponseDto.of(card);
    }

    public List<CardResponseDto> getCards() {
        List<Card> cardList = cardRepository.findAll();
        return cardList.stream()
            .map(CardResponseDto::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CardResponseDto getCard(Long cardId) {
        Card card = findByCard(cardId);
        return CardResponseDto.of(card);
    }

    @Transactional
    public CardResponseDto modifyCard(Long cardId, CardRequestDto requestDto, User user) {
        Card card = findByCard(cardId);
        checkAuthority(user, card);

        if (!card.getUser().getId().equals(user.getId())){
            throw new NotFoundWorker("userId",user.getId().toString(),"수정 권한 오류");
        }

        if(card != null){
            card.updateTitle(requestDto.getTitle());
            card.updateModifiedAt(LocalDateTime.now());

            cardRepository.save(card);
            return new CardResponseDto(card);
        }else{
            throw new ServiceException(ErrorCode.NOT_FOUND_CARD);
        }
    }

    @Transactional
    public void deleteCard(Long cardId, User user) {
        Card card = findByCard(cardId);
        checkAuthority(user, card);

        if (card != null) {
            cardRepository.deleteById(cardId);
        } else {
            throw new ServiceException(ErrorCode.NOT_FOUND_CARD);
        }
    }

    private static void checkAuthority(User user, Card card) {
        if(!card.getUser().getId().equals(user.getId())){
            throw new NotFoundWorker("userId", user.getId().toString(),"작성자가 아닙니다.");
        }
    }

    private Card findByCard(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(
            () -> new NotFoundCardException("cardId", cardId.toString(), "Card를 찾을 수 없습니다.")
        );
    }

}
