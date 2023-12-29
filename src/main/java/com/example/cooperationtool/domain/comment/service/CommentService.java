package com.example.cooperationtool.domain.comment.service;

import com.example.cooperationtool.domain.comment.dto.request.CommentRequestDto;
import com.example.cooperationtool.domain.comment.dto.response.CommentResponseDto;
import com.example.cooperationtool.domain.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

public interface CommentService {

    CommentResponseDto createComment(CommentRequestDto commentRequestDto, User user, Long cardId);

    CommentResponseDto updateComment(CommentRequestDto commentRequestDto, User user, Long commentId);

    CommentResponseDto deleteComment(User user, Long commentId);
}
