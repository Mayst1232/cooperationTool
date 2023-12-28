package com.example.cooperationtool.domain.card.controller;

import com.example.cooperationtool.domain.card.dto.CardRequestDto;
import com.example.cooperationtool.domain.card.dto.CardResponseDto;
import com.example.cooperationtool.domain.card.exception.NotFoundCardException;
import com.example.cooperationtool.domain.card.service.CardService;
import com.example.cooperationtool.global.dto.response.RootResponseDto;
import com.example.cooperationtool.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<RootResponseDto> createCard(@RequestBody CardRequestDto cardRequestDto, @AuthenticationPrincipal
        UserDetailsImpl userDetails){
        try {
            RootResponseDto rootResponseDto = cardService.createCard(cardRequestDto,userDetails.getUser());
            return ResponseEntity.ok().body(rootResponseDto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CardResponseDto>> getCards(@AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            List<CardResponseDto> cardResponseDtos = cardService.getCards(userDetails.getUser());
            return ResponseEntity.ok().body(cardResponseDtos);
        }catch (NotFoundCardException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<RootResponseDto> getCard(@PathVariable Long cardId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            RootResponseDto rootResponseDto = cardService.getCard(cardId, userDetails.getUser());
            return ResponseEntity.ok().body(rootResponseDto);
        }catch (NotFoundCardException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).build();
        }
    }

    @PatchMapping("/{cardId}")
    public ResponseEntity<RootResponseDto> modifyCard(@PathVariable Long cardId, @RequestBody CardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            RootResponseDto rootResponseDto = cardService.modifyCard(cardId,requestDto,userDetails.getUser());
            return ResponseEntity.ok().body(rootResponseDto);
        }catch (NotFoundCardException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).build();
        }
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<String> deleteCard(@PathVariable Long cardId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            cardService.deleteCard(cardId, userDetails.getUser());
            return ResponseEntity.ok().body("성공적으로 삭제되었습니다.");
        }catch (NullPointerException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).build();
        }
    }
}
