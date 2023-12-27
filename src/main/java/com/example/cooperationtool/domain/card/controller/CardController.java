package com.example.cooperationtool.domain.card.controller;

import com.example.cooperationtool.domain.card.dto.CardRequestDto;
import com.example.cooperationtool.domain.card.dto.CardResponseDto;
import com.example.cooperationtool.domain.card.exception.NotFoundCardException;
import com.example.cooperationtool.domain.card.service.CardService;
import com.example.cooperationtool.global.dto.response.RootResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<RootResponseDto> createCard(@RequestBody CardRequestDto cardRequestDto){
        try {
            RootResponseDto rootResponseDto = cardService.createCard(cardRequestDto);
            return ResponseEntity.ok().body(rootResponseDto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CardResponseDto>> getCards(){
        try {
            List<CardResponseDto> cardResponseDtos = cardService.getCards();
            return ResponseEntity.ok().body(cardResponseDtos);
        }catch (NotFoundCardException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RootResponseDto> getCard(@PathVariable Long id){
        try {
            RootResponseDto rootResponseDto = cardService.getCard(id);
            return ResponseEntity.ok().body(rootResponseDto);
        }catch (NotFoundCardException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }
}
