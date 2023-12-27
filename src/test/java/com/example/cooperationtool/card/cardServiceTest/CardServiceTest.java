package com.example.cooperationtool.card.cardServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import com.example.cooperationtool.domain.card.dto.CardRequestDto;
import com.example.cooperationtool.domain.card.entity.Card;
import com.example.cooperationtool.domain.card.repository.CardRepository;
import com.example.cooperationtool.domain.card.service.CardService;
import com.example.cooperationtool.global.dto.response.RootResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CardServiceTest {

    @InjectMocks
    private CardService cardService;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private CardRequestDto cardRequestDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTest() {
        //given
        given(cardRequestDto.getTitle()).willReturn("testTitle");

        Card saveCard = Card.builder()
            .id(1L)
            .title("testTitle")
            .build();

        given(cardRepository.save(saveCard)).willReturn(saveCard);

        //when
        RootResponseDto responseDto = cardService.createCard(cardRequestDto);

        //then
        assertEquals("200",responseDto.code());
        assertEquals("생성 완료",responseDto.message());
    }
}
