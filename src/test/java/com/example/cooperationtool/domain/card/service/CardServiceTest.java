package com.example.cooperationtool.domain.card.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import com.example.cooperationtool.domain.card.dto.CardResponseDto;
import com.example.cooperationtool.domain.card.entity.Card;
import com.example.cooperationtool.domain.card.repository.CardRepository;
import com.example.cooperationtool.domain.user.entity.User;
import com.example.cooperationtool.domain.user.entity.UserRoleEnum;
import com.example.cooperationtool.global.security.UserDetailsImpl;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
        // given
        User mockUser = User.builder().username("tesUser").password("testPassword").role(UserRoleEnum.USER).build();
        UserDetailsImpl mockUserDetails = new UserDetailsImpl(mockUser);
        Card savedCard = Card.builder().id(1L).title("testTitle").build();

        given(userDetails.getUser()).willReturn(mockUserDetails.getUser());
        given(cardRepository.save(savedCard)).willReturn(savedCard);
        given(cardRepository.findById(savedCard.getId())).willReturn(Optional.of(savedCard));
        // when
        Optional<Card> response = cardRepository.findById(savedCard.getId());

        // then
        System.out.println(response.get().getTitle());
        assertEquals("testTitle",response.get().getTitle());
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
        List<CardResponseDto> cardList = cardService.getCards(mockUser);

        //then
        System.out.println(cardList);
    }
}
