package com.example.cooperationtool.domain.card.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.example.cooperationtool.domain.card.dto.CardRequestDto;
import com.example.cooperationtool.domain.card.dto.CardResponseDto;
import com.example.cooperationtool.domain.card.entity.Card;
import com.example.cooperationtool.domain.card.repository.CardRepository;
import com.example.cooperationtool.domain.user.entity.User;
import com.example.cooperationtool.domain.user.entity.UserRoleEnum;
import com.example.cooperationtool.global.dto.response.RootResponseDto;
import com.example.cooperationtool.global.security.UserDetailsImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    UserDetailsImpl userDetails;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Card 생성")
    void createTest() {
        //given
        User mockUser = User.builder().username("testUserName").password("testPassword").role(UserRoleEnum.USER).build();
        UserDetailsImpl mockUserDetails = new UserDetailsImpl(mockUser);
        given(userDetails.getUser()).willReturn(mockUserDetails.getUser());

        Card saveCard = Card.builder().id(1L).title("testTitle1").build();
        CardRequestDto requestDto = new CardRequestDto("testTitle");

        given(cardRepository.save(saveCard)).willReturn(saveCard);
        //when
        RootResponseDto card = cardService.createCard(requestDto, mockUser);

        //then
        verify(cardRepository).save(saveCard);
    }

    @Test
    @DisplayName("Card 전체조회")
    void testGets(){
        //given
        User mockUser = User.builder().username("testUser").password("testPassword").role(UserRoleEnum.USER).build();
        UserDetailsImpl mockUserDetails = new UserDetailsImpl(mockUser);
        Card saveCard = Card.builder().id(1L).title("testTitle").build();
        Card saveCard1 = Card.builder().id(1L).title("testTitle1").build();

        given(userDetails.getUser()).willReturn(mockUserDetails.getUser());
        given(cardRepository.save(saveCard)).willReturn(saveCard);
        given(cardRepository.save(saveCard1)).willReturn(saveCard1);
        given(cardRepository.findAll()).willReturn(Arrays.asList(saveCard, saveCard1));
        //when
        cardRepository.save(saveCard);
        cardRepository.save(saveCard1);
        List<CardResponseDto> cards = cardService.getCards(mockUser);

        //then
        cards.forEach(card -> System.out.println(card.getTitle()));
        verify(cardRepository).findAll();
    }
}
