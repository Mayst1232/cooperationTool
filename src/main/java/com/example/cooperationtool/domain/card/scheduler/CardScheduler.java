package com.example.cooperationtool.domain.card.scheduler;

import com.example.cooperationtool.domain.card.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Scheduler")
@Component
@RequiredArgsConstructor
public class CardScheduler {

    private final CardService cardService;

    @Scheduled(cron = "0 0 1 * * *")
    public void updateDday(){
        Long cardId = null;
        Long dday = null;
        log.info("Update D-day");
        cardService.updateAllCardDueDates(cardId,dday);
    }

}
