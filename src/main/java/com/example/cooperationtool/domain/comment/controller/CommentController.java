package com.example.cooperationtool.domain.comment.controller;

import com.example.cooperationtool.domain.comment.dto.request.CommentRequestDto;
import com.example.cooperationtool.domain.comment.dto.response.CommentResponseDto;
import com.example.cooperationtool.domain.comment.entity.Comment;
import com.example.cooperationtool.domain.comment.service.CommentService;
import com.example.cooperationtool.global.dto.response.RootResponseDto;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> createComment(
        @RequestBody CommentRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long cardId = requestDto.getCardId();

        CommentResponseDto responseDto = commentService.createComment(requestDto,userDetails.getUser(), cardId);
        return ResponseEntity.ok(RootResponseDto.builder()
            .code("200")
            .message("댓글 생성 성공")
            .data(responseDto)
            .build()
        );
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<?> updateComment(
        @PathVariable Long commentId,
        @RequestBody CommentRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.updateComment(requestDto, userDetails.getUser(), commentId);

        return  ResponseEntity.ok(RootResponseDto.builder()
            .code("200")
            .message("댓글 수정 성공")
            .data(responseDto)
            .build()
        );
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long commentId) {

        CommentResponseDto responseDto = commentService.deleteComment(userDetails.getUser(), commentId);

        return ResponseEntity.ok(RootResponseDto.builder()
            .code("200")
            .message("댓글 삭제 성공")
            .build()
        );
    }

    @GetMapping
    public ResponseEntity<?> getComment(
        @RequestBody CommentRequestDto requestDto) {

        Long cardId = requestDto.getCardId();

        List<CommentResponseDto> commentResponseDtoList = commentService.getComment(cardId);
        return ResponseEntity.ok(RootResponseDto.builder()
            .code("200")
            .message("댓글 조회 성공")
            .data(commentResponseDtoList)
            .build()
        );
    }
}

