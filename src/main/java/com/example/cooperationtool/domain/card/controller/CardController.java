package com.example.cooperationtool.domain.card.controller;

import com.example.cooperationtool.domain.card.dto.CardRequestDto;
import com.example.cooperationtool.domain.card.dto.CardResponseDto;
import com.example.cooperationtool.domain.card.service.CardService;
import com.example.cooperationtool.global.dto.response.RootResponseDto;
import com.example.cooperationtool.global.security.UserDetailsImpl;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        CardResponseDto responseDto = cardService.createCard(cardRequestDto,
            userDetails.getUser());
        return ResponseEntity.ok().body(RootResponseDto.builder()
            .code("201")
            .message("생성 성공")
            .data(responseDto)
            .build());
    }

    @GetMapping
    public ResponseEntity<List<?>> getCards(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<CardResponseDto> cardResponseDtos = cardService.getCards(userDetails.getUser());
        return ResponseEntity.ok().body(Collections.singletonList(RootResponseDto.builder()
            .code("200")
            .message("조회 성공")
            .data(cardResponseDtos)
            .build()));
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<?> getCard(@PathVariable Long cardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardResponseDto cardResponseDto = cardService.getCard(cardId,userDetails.getUser());
        return ResponseEntity.ok().body(RootResponseDto.builder()
            .code("200")
            .message("조회 성공")
            .data(cardResponseDto)
            .build());
    }

    @PatchMapping("/{cardId}")
    public ResponseEntity<?> modifyCard(@PathVariable Long cardId,
        @RequestBody CardRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardResponseDto cardResponseDto = cardService.modifyCard(cardId, requestDto,
            userDetails.getUser());
        return ResponseEntity.ok().body(RootResponseDto.builder()
            .code("200")
            .message("수정 성공")
            .data(cardResponseDto)
            .build());
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> deleteCard(@PathVariable Long cardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        cardService.deleteCard(cardId, userDetails.getUser());
        return ResponseEntity.ok().body("성공적으로 삭제되었습니다.");
    }

    @PostMapping("/{cardId}/invite")
    public ResponseEntity<?> inviteWorker(@PathVariable Long cardId,
        @RequestParam Long userId) {
        cardService.inviteWorker(cardId, userId);
        return ResponseEntity.ok().body("성공적으로 초대하였습니다.");
    }

    @DeleteMapping("/{cardId}/invite")
    public ResponseEntity<?> deleteWorker(@PathVariable Long cardId,
        @RequestParam Long userId) {
        cardService.inviteCancel(cardId, userId);
        return ResponseEntity.ok().body("성공적으로 삭제되었습니다.");
    }

    @PostMapping("/date/{cardId}")
    public ResponseEntity<?> DdayCards(@PathVariable Long cardId,@RequestParam Long dday) {
        CardResponseDto cardResponseDto = cardService.updateAllCardDueDates(cardId, dday);
        return ResponseEntity.ok().body(RootResponseDto.builder()
            .code("201")
            .message("D-day 설정 완료")
            .data(cardResponseDto)
            .build());
    }
}