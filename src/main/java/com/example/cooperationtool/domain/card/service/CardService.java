package com.example.cooperationtool.domain.card.service;

import com.example.cooperationtool.domain.card.dto.CardRequestDto;
import com.example.cooperationtool.domain.card.dto.CardResponseDto;
import com.example.cooperationtool.domain.card.entity.Card;
import com.example.cooperationtool.domain.card.entity.InviteCard;
import com.example.cooperationtool.domain.card.repository.CardRepository;
import com.example.cooperationtool.domain.card.repository.InviteCardRepository;
import com.example.cooperationtool.domain.column.repository.ColumnRepository;
import com.example.cooperationtool.domain.user.entity.User;
import com.example.cooperationtool.domain.user.repository.UserRepository;
import com.example.cooperationtool.global.exception.ErrorCode;
import com.example.cooperationtool.global.exception.ServiceException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

        if (column.getUser().getId().equals(user.getId())) {
            Card card = cardRepository.save(Card.builder()
                .user(user)
                .priority(0L)
                .columns(column)
                .title(cardRequestDto.getTitle())
                .build());

            card.setPriority(card.getId());
            cardRepository.save(card);

            InviteCard inviteCard = InviteCard.builder()
                .card(card)
                .user(user)
                .build();
            inviteCardRepository.save(inviteCard);

            return new CardResponseDto(card);
        } else {
            throw new ServiceException(ErrorCode.NOT_FOUND_COLUMN);
        }
    }

    public List<CardResponseDto> getCards(User user) {
        List<Card> cardList = cardRepository.findCardsByUserIdWithInvites(user.getId());

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
        Optional<Card> optionalCard = cardRepository.findBycardAndUserIdWithInvites(cardId,
            user.getId());

        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();

            return CardResponseDto.of(card);
        }else{
            throw new ServiceException(ErrorCode.NOT_FOUND_CARD);
        }
    }

    @Transactional
    public CardResponseDto modifyCard(Long cardId, CardRequestDto requestDto, User user) {
        var card = findByCardId(cardId);
        checkAuthority(user, card);

        if (!card.getUser().getId().equals(user.getId())) {
            throw new ServiceException(ErrorCode.NOT_AUTHORIZATION);
        }

        if (card != null) {
            card.updateTitle(requestDto.getTitle());
            card.updateModifiedAt(LocalDateTime.now());

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

    public void inviteWorker(Long cardId, User user, Long userId) {
        var byCard = findByCardId(cardId);
        checkAuthority(user,byCard);

        var byUser = userRepository.findById(userId).orElseThrow(
            () -> new ServiceException(ErrorCode.NOT_EXIST_USER)
        );

        var getInviteCardList = inviteCardRepository.findByCardId(cardId);
        boolean isAlreadyInvited = getInviteCardList.stream()
            .anyMatch(inviteCard -> inviteCard.getUser().getId().equals(userId));

        if (!isAlreadyInvited) {
            InviteCard saveInvite = InviteCard.builder()
                .card(byCard)
                .user(byUser)
                .build();

            inviteCardRepository.save(saveInvite);
        } else {
            throw new ServiceException(ErrorCode.DUPLICATE_USERNAME);
        }
    }

    @Transactional
    public void inviteCancel(Long cardId, User user, Long userId) {
        List<InviteCard> byId = inviteCardRepository.findByCardId(cardId);
        var card = findByCardId(cardId);
        checkAuthority(user,card);

        boolean cards= byId.stream().anyMatch(inviteCard -> inviteCard.getUser().getId().equals(userId));

        if (cards) {
            inviteCardRepository.deleteByCardIdAndUserId(card.getId(), user.getId());
        } else {
            throw new ServiceException(ErrorCode.NOT_FOUND_CARD);
        }
    }

    @Transactional
    public CardResponseDto updateAllCardDueDates(Long cardId, Long dday, User user) {
        var byCard = findByCardId(cardId);

        var byCardId = inviteCardRepository.findByCardId(cardId);
        boolean ListId = byCardId.stream().anyMatch(ListCardId -> ListCardId.getCard().getUser().getId().equals(user.getId()));

        if (ListId || user.getId().equals(byCard.getUser().getId())){
            updateCardDday(byCard, dday);
        } else {
            throw new ServiceException(ErrorCode.NOT_FOUND_CARD);
        }
        return new CardResponseDto(byCard);
    }

    @Transactional
    public void updateCardDday(Card card, Long dday) {
        LocalDate today = LocalDate.now();
        LocalDate dueDate = today.plusDays(dday);

        card.updateDday(dday.intValue());
        card.updateDueDate(dueDate.atStartOfDay());
    }

    @Transactional
    public List<CardResponseDto> moveCard(Long cardId, User user, Long moveNumber) {
        var card = findByCardId(cardId);
        var byId = inviteCardRepository.findByCardId(cardId);

        if(!byId.isEmpty() && byId.get(0).getUser().getId().equals(user
            .getId()) || user.getId().equals(card.getUser().getId())){

            Long currentPriority = card.getPriority();
            Long moveDirection;
            Long start;
            Long end;

            if (currentPriority > moveNumber) {
                moveDirection = 1L;
                start = moveNumber;
                end = currentPriority;
            } else if (currentPriority < moveNumber) {
                moveDirection = -1L;
                start = currentPriority;
                end = moveNumber;
            } else {
                moveDirection = 0L;
                start = moveNumber;
                end = moveNumber;
            }

            cardRepository.moveCard(start, end, moveDirection);

            card.move(moveNumber);

            List<Card> cardList = cardRepository.findAllOrderByPriority(card.getColumns().getId());

            return cardList.stream().map(CardResponseDto::new).collect(Collectors.toList());
        }else{
            throw new ServiceException(ErrorCode.NOT_FOUND_CARD);
        }
    }

    private static void checkAuthority(User user, Card card) {
        if (!card.getUser().getId().equals(user.getId())) {
            throw new ServiceException(ErrorCode.NOT_AUTHORIZATION);
        }
    }

    private Card findByCardId(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(
            () -> new ServiceException(ErrorCode.NOT_FOUND_CARD)
        );
    }
}
