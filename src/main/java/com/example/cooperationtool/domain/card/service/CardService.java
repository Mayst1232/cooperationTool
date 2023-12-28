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
            Card saveCard = Card.builder().title(cardRequestDto.getTitle()).build();
            saveCard.setUser(user);
            Card card = cardRepository.save(saveCard);

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
        Card card = findByCard(cardId, user);
        return new RootResponseDto("200", "조회 완료", card);
    }

    @Transactional
    public RootResponseDto modifyCard(Long cardId, CardRequestDto requestDto, User user) {
        Card card = findByCard(cardId, user);
        card.setUser(user);
        card.setTitle(requestDto.getTitle());
        if (card != null) {
            card.setUser(user);
            cardRepository.save(card);
            return new RootResponseDto("200", "수정 성공",null);
        } else {
            throw new NotFoundCardException("cardId", cardId.toString(), "Card를 찾을 수 없습니다.");
        }
    }

    @Transactional
    public RootResponseDto deleteCard(Long cardId, User user) {
        Card card = findByCard(cardId,user);
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

        return cardRepository.findById(userId).orElseThrow(
            () -> new NotFoundWorker("userId",user.getId().toString(),"UserId를 찾을 수 없음")
        ).getUser();
    }

    private Card findByCard(Long cardId, User user) {
        return cardRepository.findByIdAndUser(cardId,user).orElseThrow(
            () -> new NotFoundCardException("cardId", cardId.toString(), "Card를 찾을 수 없습니다.")
        );
    }

}
