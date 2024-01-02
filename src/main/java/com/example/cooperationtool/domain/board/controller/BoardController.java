package com.example.cooperationtool.domain.board.controller;

import com.example.cooperationtool.domain.board.dto.response.BoardViewResponseDto;
import com.example.cooperationtool.domain.board.dto.request.BoardRequestDto;
import com.example.cooperationtool.domain.board.dto.request.InviteBoardRequestDto;
import com.example.cooperationtool.domain.board.dto.response.BoardResponseDto;
import com.example.cooperationtool.domain.board.service.BoardService;
import com.example.cooperationtool.global.dto.response.RootResponseDto;
import com.example.cooperationtool.global.security.UserDetailsImpl;
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
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/boards")
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

    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity<?> deleteBoard(
        @PathVariable Long boardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.deleteBoard(boardId, userDetails.getUser());
        return ResponseEntity.ok(RootResponseDto.builder()
                .code("200")
                .message("보드 삭제 성공")
            .build());
    }

    @PatchMapping("/boards/{boardId}")
    public ResponseEntity<?> updateBoard(
        @PathVariable Long boardId,
        @RequestBody BoardRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BoardResponseDto responseDto =
            boardService.updateBoard(boardId, requestDto, userDetails.getUser());
        return ResponseEntity.ok(RootResponseDto.builder()
                .code("200")
                .message("보드 수정 성공")
                .data(responseDto)
            .build());
    }

    @GetMapping("/boards")
    public ResponseEntity<?> getBoards(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestParam(name = "type", required = true, defaultValue = "all") String type) {
        List<BoardViewResponseDto> responseDto = boardService.getBoards(userDetails.getUser(), type);
        return ResponseEntity.ok(RootResponseDto.builder()
                .code("200")
                .message("보드 조회 성공")
                .data(responseDto)
            .build());
    }

    @PostMapping("/boards/{boardId}/invite")
    public ResponseEntity<?> inviteBoard(
        @PathVariable (name = "boardId") Long boardId,
        @RequestBody InviteBoardRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.inviteBoard(boardId, requestDto.getUserId(), userDetails.getUser());
        return ResponseEntity.ok(RootResponseDto.builder()
                .code("200")
                .message("유저 초대 성공")
            .build());
    }

    @DeleteMapping("/boards/{boardId}/invite")
    public ResponseEntity<?> unInviteBoard(
        @PathVariable (name = "boardId") Long boardId,
        @RequestBody InviteBoardRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.unInviteBoard(boardId, requestDto.getUserId(), userDetails.getUser());
        return ResponseEntity.ok(RootResponseDto.builder()
                .code("200")
                .message("보드 초대 취소")
            .build());
    }
}
