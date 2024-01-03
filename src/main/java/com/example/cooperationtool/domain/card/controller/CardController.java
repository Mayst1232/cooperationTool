package com.example.cooperationtool.domain.card.controller;

import com.example.cooperationtool.domain.card.dto.CardRequestDto;
import com.example.cooperationtool.domain.card.dto.CardResponseDto;
import com.example.cooperationtool.domain.card.dto.GetCardRequestDto;
import com.example.cooperationtool.domain.card.dto.InviteResponseDto;
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
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<List<?>> getCards(@RequestBody GetCardRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<CardResponseDto> cardResponseDtos = cardService.getCards(requestDto.getBoardId(),
            userDetails.getUser());
        return ResponseEntity.ok().body(Collections.singletonList(RootResponseDto.builder()
            .code("200")
            .message("조회 성공")
            .data(cardResponseDtos)
            .build()));
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<?> getCard(@PathVariable Long cardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardResponseDto cardResponseDto = cardService.getCard(cardId, userDetails.getUser());
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
        return ResponseEntity.ok().body(RootResponseDto.builder()
            .code("200")
            .message("성공적으로 삭제되었습니다")
            .build());
    }

    @PostMapping("/{cardId}/invite")
    public ResponseEntity<?> inviteWorker(@PathVariable Long cardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestParam Long userId) {
        cardService.inviteWorker(cardId, userDetails.getUser(), userId);
        return ResponseEntity.ok().body(InviteResponseDto.builder()
            .code("200")
            .message(userId + "번 유저가 " + cardId + "번 카드에 초대되었습니다.")
            .build());
    }

    @DeleteMapping("/{cardId}/invite")
    public ResponseEntity<?> deleteWorker(@PathVariable Long cardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestParam Long userId) {
        cardService.inviteCancel(cardId, userDetails.getUser(), userId);
        return ResponseEntity.ok().body(InviteResponseDto.builder()
            .code("200")
            .message(userId + "번 유저가 " + cardId + "번 카드에서 삭제되었습니다.")
            .build());
    }

    @PutMapping("/date/{cardId}")
    public ResponseEntity<?> dDayCards(@PathVariable Long cardId, @RequestParam Long dday,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardResponseDto cardResponseDto = cardService.updateAllCardDueDates(cardId, dday,
            userDetails.getUser());
        return ResponseEntity.ok().body(RootResponseDto.builder()
            .code("201")
            .message("D-day 설정 완료")
            .data(cardResponseDto)
            .build());
    }

    @PatchMapping("/move/{cardId}")
    public ResponseEntity<List<?>> moveCard(@PathVariable Long cardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestParam Long moveNumber) {
        List<CardResponseDto> cardResponseDto = cardService.moveCard(cardId, userDetails.getUser(),
            moveNumber);
        return ResponseEntity.ok().body(Collections.singletonList(RootResponseDto.builder()
            .code("201")
            .message("성공적으로 수정되었습니다.")
            .data(cardResponseDto)
            .build()));
    }
}