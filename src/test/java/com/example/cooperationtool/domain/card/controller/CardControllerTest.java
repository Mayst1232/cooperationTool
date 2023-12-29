package com.example.cooperationtool.domain.card.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.example.cooperationtool.domain.card.dto.CardRequestDto;
import com.example.cooperationtool.domain.card.dto.CardResponseDto;
import com.example.cooperationtool.domain.card.service.CardService;
import com.example.cooperationtool.domain.user.entity.User;
import com.example.cooperationtool.domain.user.entity.UserRoleEnum;
import com.example.cooperationtool.global.dto.response.RootResponseDto;
import com.example.cooperationtool.global.security.UserDetailsImpl;
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

    @Mock
    UserDetailsImpl userDetails;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Card 생성")
    void testCreateCardSuccess() {
        // Given
        CardRequestDto requestDto = new CardRequestDto("Test Card Title");

        User mockUser = User.builder().username("testUser").password("testPassword")
            .role(UserRoleEnum.USER).build();

        UserDetailsImpl mockUserDetails = new UserDetailsImpl(mockUser);

        given(userDetails.getUser()).willReturn(mockUserDetails.getUser());

        // When
        ResponseEntity<?> response = cardController.createCard(requestDto, mockUserDetails);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        verify(cardService).createCard(requestDto, mockUser);//controller -> service
    }

    @Test
    @DisplayName("Card 전체조회")
    void testGets(){
        //given
        List<CardResponseDto> cardResponseDto = Collections.singletonList(
            CardResponseDto.builder().title("TestCard").build()
        );
        User mockUser = User.builder().username("testUser").password("testPassword")
            .role(UserRoleEnum.USER).build();

        UserDetailsImpl mockUserDetails = new UserDetailsImpl(mockUser);

        given(userDetails.getUser()).willReturn(mockUserDetails.getUser());

        //when
        ResponseEntity<List<?>> response = cardController.getCards();

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(cardService).getCards();
    }

    @Test
    @DisplayName("Card 단건조회")
    void testGet(){
        //give
        Long cardId = 1L;
        RootResponseDto rootResponseDto = new RootResponseDto("200","조회 완료", null);

        User mockUser = User.builder().username("testUser").password("testPassword")
            .role(UserRoleEnum.USER).build();

        UserDetailsImpl mockUserDetails = new UserDetailsImpl(mockUser);

        given(userDetails.getUser()).willReturn(mockUserDetails.getUser());
        //when
        ResponseEntity<?> response = cardController.getCard(cardId);

        //then
        System.out.println("response.getBody() = " + response.getBody());
        assertEquals(rootResponseDto.code(),"200");
        verify(cardService).getCard(cardId);
    }

    @Test
    @DisplayName("Card 수정")
    void modifyCard(){
        //given
        Long cardId = 1L;
        CardRequestDto requestDto = new CardRequestDto("UpdateTitle");

        User mockUser = User.builder().username("testUser").password("testPassword").role(UserRoleEnum.USER).build();
        UserDetailsImpl mockUserDetails = new UserDetailsImpl(mockUser);

        given(userDetails.getUser()).willReturn(mockUserDetails.getUser());
        //when
        ResponseEntity<?> response = cardController.modifyCard(cardId, requestDto, mockUserDetails);

        //then
        assertEquals(response.getStatusCode().toString(), "200 OK");
        verify(cardService).modifyCard(cardId, requestDto, mockUser);
    }

    @Test
    @DisplayName("Card 삭제")
    void deleteTest(){
        //given
        Long cardId = 1L;

        User mockUser = User.builder().username("testUser").password("testPassword").role(UserRoleEnum.USER).build();
        UserDetailsImpl mockUserDetails = new UserDetailsImpl(mockUser);

        given(userDetails.getUser()).willReturn(mockUserDetails.getUser());
        //when
        ResponseEntity<?> response = cardController.deleteCard(cardId, mockUserDetails);

        //then
        assertEquals(response.getStatusCode().toString(),"200 OK");
        verify(cardService).deleteCard(cardId,mockUser);
    }
}