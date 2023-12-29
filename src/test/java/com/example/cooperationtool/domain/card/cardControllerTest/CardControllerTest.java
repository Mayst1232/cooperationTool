//package com.example.cooperationtool.card.cardControllerTest;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.BDDMockito.given;
//
//import com.example.cooperationtool.domain.card.controller.CardController;
//import com.example.cooperationtool.domain.card.dto.CardRequestDto;
//import com.example.cooperationtool.domain.card.dto.CardResponseDto;
//import com.example.cooperationtool.domain.card.service.CardService;
//import com.example.cooperationtool.domain.user.entity.User;
//import com.example.cooperationtool.global.dto.response.RootResponseDto;
//import java.util.Collections;
//import java.util.List;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//public class CardControllerTest {
//
//    @InjectMocks
//    private CardController cardController;
//
//    @Mock
//    private CardService cardService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    @DisplayName("Card 생성")
//    void testCreateCardSuccess() {
//        //given
//        CardRequestDto requestDto = new CardRequestDto("testtitle");
//        CardResponseDto cardResponseDto = new CardResponseDto(1L, "testtitle"); // Complete the instantiation
//
//        User user = User.builder().username("username").password("password").build();
//
//        //when
//        given(cardService.createCard(requestDto, user)).willReturn(cardResponseDto);
//        ResponseEntity<CardResponseDto> response = cardController.createCard(requestDto, user);
//
//        //then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(cardResponseDto, response.getBody());
//    }
//
//    @Test
//    @DisplayName("Card 전체조회")
//    void testGets() {
//        //given
//        List<CardResponseDto> cardResponseDtoList = Collections.singletonList(
//            CardResponseDto.builder().cardId(1L).title("TestCard").build()
//        );
//
//        //when
//        given(cardService.getCards()).willReturn(cardResponseDtoList);
//        ResponseEntity<List<?>> response = cardController.getCards();
//
//        //then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(cardResponseDtoList, response.getBody());
//    }
//
//    @Test
//    @DisplayName("Card 단건조회")
//    void testGet(){
//        //given
//        CardResponseDto cardResponseDto;
//
//        //when
//
//
//        //then
//    }
//}