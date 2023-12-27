package com.example.cooperationtool.card.cardControllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import com.example.cooperationtool.domain.card.controller.CardController;
import com.example.cooperationtool.domain.card.dto.CardRequestDto;
import com.example.cooperationtool.domain.card.dto.CardResponseDto;
import com.example.cooperationtool.domain.card.service.CardService;
import com.example.cooperationtool.global.dto.response.RootResponseDto;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CardControllerTest {

    @InjectMocks
    private CardController cardController;

    @Mock
    private CardService cardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Card 생성")
    void testCreateCardSuccess() {
        //given
        CardRequestDto requestDto = new CardRequestDto("testtitle");
        RootResponseDto responseDto = new RootResponseDto("200", "생성 성공", requestDto);

        //when
        given(cardService.createCard(requestDto)).willReturn(responseDto);
        ResponseEntity<RootResponseDto> response = cardController.createCard(requestDto);

        //then
        System.out.println("response.getBody() = " + response.getBody().code());
        System.out.println("response = " + response.getBody().code());
        assertEquals(response.getBody().code(),responseDto.code());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Card 전체조회")
    void testGets(){
        //given
        List<CardResponseDto> cardResponseDto = Collections.singletonList(
            CardResponseDto.builder().title("TestCard").build()
        );
        //when
        given(cardService.getCards()).willReturn(cardResponseDto);
        ResponseEntity<List<CardResponseDto>> response = cardController.getCards();

        //then
        assertEquals(cardResponseDto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Card 단건조회")
    void testGet(){
        //given
        CardResponseDto cardResponseDto;

        //when


        //then
    }
}