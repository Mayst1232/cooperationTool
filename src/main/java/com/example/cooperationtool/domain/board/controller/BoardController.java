package com.example.cooperationtool.domain.board.controller;

import com.example.cooperationtool.domain.board.dto.request.BoardRequestDto;
import com.example.cooperationtool.domain.board.dto.response.BoardResponseDto;
import com.example.cooperationtool.domain.board.service.BoardService;
import com.example.cooperationtool.domain.user.entity.User;
import com.example.cooperationtool.global.dto.response.RootResponseDto;
import com.example.cooperationtool.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("boards")
    public ResponseEntity<?> createBoard(
        @RequestBody BoardRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BoardResponseDto responseDto = boardService.createBoard(requestDto, userDetails.getUser());
        return ResponseEntity.ok(RootResponseDto.builder()
                .code("201")
                .message("보드 생성 성공")
                .data(responseDto)
            .build());
    }

    @DeleteMapping("boards/{boardId}")
    public ResponseEntity<?> deleteBoard(
        @PathVariable Long boardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.deleteBoard(boardId, userDetails.getUser());
        return ResponseEntity.ok(RootResponseDto.builder()
                .code("200")
                .message("보드 삭제 성공")
            .build());
    }
}
