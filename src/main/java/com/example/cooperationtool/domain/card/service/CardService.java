package com.example.cooperationtool.domain.card.service;

import com.example.cooperationtool.domain.card.dto.CardRequestDto;
import com.example.cooperationtool.domain.card.dto.CardResponseDto;
import com.example.cooperationtool.domain.card.entity.Card;
import com.example.cooperationtool.domain.card.entity.InviteCard;
import com.example.cooperationtool.domain.card.repository.CardRepository;
import com.example.cooperationtool.domain.card.repository.InviteCardRepository;
import com.example.cooperationtool.domain.column.entity.Columns;
import com.example.cooperationtool.domain.column.repository.ColumnRepository;
import com.example.cooperationtool.domain.user.entity.User;
import com.example.cooperationtool.domain.user.repository.UserRepository;
import com.example.cooperationtool.global.exception.ErrorCode;
import com.example.cooperationtool.global.exception.ServiceException;
import java.time.LocalDate;
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
    private final ColumnRepository columnRepository;
    private final InviteCardRepository inviteCardRepository;
    private final UserRepository userRepository;

    public CardResponseDto createCard(CardRequestDto cardRequestDto, User user) {

        var column = columnRepository.findById(cardRequestDto.getColumnsId()).orElseThrow(
            () -> new ServiceException(ErrorCode.NOT_FOUND_BOARD)
        );

        Card card = cardRepository.save(Card.builder()
            .user(user)
            .columns(column)
            .title(cardRequestDto.getTitle())
            .build());

        return new CardResponseDto(card);
    }

    public List<CardResponseDto> getCards(User user) {
        List<Card> cardList = cardRepository.findByUserId(user.getId());
        if (!cardList.isEmpty()) {
            return cardList.stream()
                .map(CardResponseDto::of)
                .collect(Collectors.toList());
        } else {
            throw new ServiceException(ErrorCode.NOT_FOUND_CARD);
        }
    }

    @Transactional(readOnly = true)
    public CardResponseDto getCard(Long cardId, User user) {
        var getCard = findByCardId(cardId);
        var getUser = findByUserId(user.getId());
        if (getCard.getUser().equals(getUser)) {
            return CardResponseDto.of(getCard);
        } else {
            throw new ServiceException(ErrorCode.NOT_FOUND_CARD);
        }
    }


    @Transactional
    public CardResponseDto modifyCard(Long cardId, CardRequestDto requestDto, User user) {
        var card = findByCardId(cardId);
        checkAuthority(user, card);

        if (!card.getUser().getId().equals(user.getId())) {
            throw new ServiceException(ErrorCode.NOT_EXIST_USER);
        }

        if (card != null) {
            card.updateTitle(requestDto.getTitle());
            card.updateModifiedAt(LocalDateTime.now());

            cardRepository.save(card);
            return new CardResponseDto(card);
        } else {
            throw new ServiceException(ErrorCode.NOT_FOUND_CARD);
        }
    }

    @Transactional
    public void deleteCard(Long cardId, User user) {
        var card = findByCardId(cardId);
        checkAuthority(user, card);

        if (card != null) {
            cardRepository.deleteById(cardId);
        } else {
            throw new ServiceException(ErrorCode.NOT_FOUND_CARD);
        }
    }

    @Transactional
    public void inviteWorker(Long cardId, Long userId) {
        var byCard = findByCardId(cardId);
        var byId = findByUserId(userId);
        InviteCard inviteCard = InviteCard.builder()
            .card(byCard)
            .user(byId)
            .build();
        inviteCardRepository.save(inviteCard);
    }

    @Transactional
    public void inviteCancel(Long cardId, Long userId) {
        var byCard = findByCardId(cardId);
        var byId = findByUserId(userId);
        if (byCard.getUser().equals(byId)) {
            inviteCardRepository.deleteByCardIdAndUserId(byCard.getId(), byId.getId());
        }
    }

    @Transactional
    public CardResponseDto updateAllCardDueDates(Long cardId, Long dday, User user) {
        var byuser = findByUserId(user.getId());
        var bycard = findByCardId(cardId);
        if (bycard.getUser().equals(byuser)) {
            updateCardDday(bycard, dday);
        } else {
            throw new ServiceException(ErrorCode.NOT_FOUND_CARD);
        }
        return new CardResponseDto(bycard);
    }

    @Transactional
    public void updateCardDday(Card card, Long dday) {
        LocalDate today = LocalDate.now();
        LocalDate dueDate = today.plusDays(dday);

        card.setDday(Math.toIntExact(dday));
        card.setDueDate(dueDate.atStartOfDay());
        cardRepository.save(card);
    }

    private static void checkAuthority(User user, Card card) {
        if (!card.getUser().getId().equals(user.getId())) {
            throw new ServiceException(ErrorCode.NOT_EXIST_USER);
        }
    }

    private Card findByCardId(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(
            () -> new ServiceException(ErrorCode.NOT_FOUND_CARD)
        );
    }

    private User findByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(
            () -> new ServiceException(ErrorCode.NOT_EXIST_USER)
        );
    }
}
