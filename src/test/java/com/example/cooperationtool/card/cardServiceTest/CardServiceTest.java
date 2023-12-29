//package com.example.cooperationtool.card.cardServiceTest;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.BDDMockito.given;
//
//import com.example.cooperationtool.domain.card.dto.CardRequestDto;
//import com.example.cooperationtool.domain.card.dto.CardResponseDto;
//import com.example.cooperationtool.domain.card.entity.Card;
//import com.example.cooperationtool.domain.card.repository.CardRepository;
//import com.example.cooperationtool.domain.card.service.CardService;
//import com.example.cooperationtool.domain.user.entity.User;
//import com.example.cooperationtool.domain.user.repository.UserRepository;
//import com.example.cooperationtool.global.dto.response.RootResponseDto;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//public class CardServiceTest {
//
//    @InjectMocks
//    private CardService cardService;
//
//    @Mock
//    private CardRepository cardRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private CardRequestDto cardRequestDto;
//
//    @Mock
//    private User user;
//
//    @BeforeEach
//    void setup() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void createTest() {
//        //given
//        given(cardRequestDto.getTitle()).willReturn("testTitle");
//        given(userRepository.findById(1L)).willReturn(java.util.Optional.of(user));
//
//        Card saveCard = Card.builder()
//            .id(1L)
//            .title("testTitle")
//            .user(user)
//            .build();
//
//        given(cardRepository.save(saveCard)).willReturn(saveCard);
//
//        //when
//        CardResponseDto responseDto = cardService.createCard(cardRequestDto, user);
//
//        //then
//        assertEquals(1L, responseDto.getCardId()); // adjust based on your actual implementation
//        assertEquals("testTitle", responseDto.getTitle());
//        // Add more assertions based on the actual properties of CardResponseDto
//    }
//}
