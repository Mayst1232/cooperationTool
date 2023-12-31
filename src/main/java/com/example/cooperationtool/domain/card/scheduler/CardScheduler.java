package com.example.cooperationtool.domain.card.scheduler;

import com.example.cooperationtool.domain.card.entity.Card;
import com.example.cooperationtool.domain.card.repository.CardRepository;
import com.example.cooperationtool.domain.card.service.CardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Scheduler")
@Component
@RequiredArgsConstructor
public class CardScheduler {

    private final CardService cardService;
    private final CardRepository cardRepository;

    @Scheduled(cron = "0 0 1 * * *")
    public void updateDday(){
        List<Card> cardList = cardRepository.findAll();
        for(Card card : cardList) {
            log.info("Card ID " + card.getId() + " D-day Before " + card.getDday());
            Long updateDday = (long) (card.getDday() - 1);
            if (card.getDday() != null && card.getDday() > 0) {
                cardService.updateAllCardDueDates(card.getId(), updateDday);
            }
            log.info("Card ID " + card.getId() + " D-day Before " + card.getDday());
        }
    }

}
