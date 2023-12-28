package com.example.cooperationtool.domain.card.service;

import com.example.cooperationtool.domain.card.dto.CardRequestDto;
import com.example.cooperationtool.domain.card.dto.CardResponseDto;
import com.example.cooperationtool.domain.card.entity.Card;
import com.example.cooperationtool.domain.card.exception.NotFoundCardException;
import com.example.cooperationtool.domain.card.exception.NotFoundWorker;
import com.example.cooperationtool.domain.card.repository.CardRepository;
import com.example.cooperationtool.domain.user.entity.User;
import com.example.cooperationtool.global.dto.response.RootResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public RootResponseDto createCard(CardRequestDto cardRequestDto, User user) {
        User byUser = findByUser(user);
        Card card = cardRepository.save(Card.builder()
            .user(byUser)
            .title(cardRequestDto.getTitle())
            .build());

        return new RootResponseDto("200", "생성 완료", card);
    }

    public List<CardResponseDto> getCards(User user) {
        findByUser(user);
        List<Card> cardList = cardRepository.findAll();
        return cardList.stream()
            .map(CardResponseDto::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RootResponseDto getCard(Long cardId, User user) {
        findByUser(user);
        Card card = findByCard(cardId);
        return new RootResponseDto("200", "조회 완료", card);
    }

    @Transactional
    public RootResponseDto modifyCard(Long cardId, CardRequestDto requestDto, User user) {
        findByUser(user);
        Card card = findByCard(cardId);
        if (card != null) {
            Card modifyCard = cardRepository.save(
                Card.builder().title(requestDto.getTitle()).build());
            return new RootResponseDto("200", "수정 성공", modifyCard);
        } else {
            throw new NotFoundCardException("cardId", cardId.toString(), "Card를 찾을 수 없습니다.");
        }
    }

    @Transactional
    public RootResponseDto deleteCard(Long cardId, User user) {
        findByUser(user);
        Card card = findByCard(cardId);
        if (card != null) {
            cardRepository.deleteById(cardId);
            return new RootResponseDto("200", "삭제완료", null);
        } else {
            throw new NotFoundCardException("cardId", cardId.toString(), "Card를 찾을 수 없습니다.");
        }
    }

    private User findByUser(User user) {
        Long userId = user.getId();
        if (userId == null){
            throw new IllegalArgumentException("user null");
        }

        var byId = cardRepository.findById(userId);
        if(byId.isPresent()){
            throw new NotFoundWorker("userId", userId.toString(), "해당 ID가 없습니다.");
        }

        return byId.get().getUser();
    }

    private Card findByCard(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(
            () -> new NotFoundCardException("cardId", cardId.toString(), "Card를 찾을 수 없습니다.")
        );
    }

}
