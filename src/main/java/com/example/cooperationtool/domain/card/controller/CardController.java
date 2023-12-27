package com.example.cooperationtool.domain.card.controller;

import com.example.cooperationtool.domain.card.dto.CardRequestDto;
import com.example.cooperationtool.domain.card.dto.CardResponseDto;
import com.example.cooperationtool.domain.card.service.CardService;
import com.example.cooperationtool.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<?> createCard(@RequestBody CardRequestDto cardRequestDto,
        @AuthenticationPrincipal
        UserDetailsImpl userDetails) {
        CardResponseDto cardResponseDto = cardService.createCard(cardRequestDto,
            userDetails.getUser());
        return ResponseEntity.ok().body(cardResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<?>> getCards() {
        List<CardResponseDto> cardResponseDtos = cardService.getCards();
        return ResponseEntity.ok().body(cardResponseDtos);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<?> getCard(@PathVariable Long cardId) {
        CardResponseDto cardResponseDto = cardService.getCard(cardId);
        return ResponseEntity.ok().body(cardResponseDto);
    }

    @PatchMapping("/{cardId}")
    public ResponseEntity<?> modifyCard(@PathVariable Long cardId,
        @RequestBody CardRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardResponseDto cardResponseDto = cardService.modifyCard(cardId, requestDto,
            userDetails.getUser());
        return ResponseEntity.ok().body(cardResponseDto);
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> deleteCard(@PathVariable Long cardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        cardService.deleteCard(cardId, userDetails.getUser());
        return ResponseEntity.ok().body("성공적으로 삭제되었습니다.");
    }
}
