package com.example.cooperationtool.domain.comment.service;

import com.example.cooperationtool.domain.comment.dto.request.CommentRequestDto;
import com.example.cooperationtool.domain.comment.dto.response.CommentResponseDto;
import com.example.cooperationtool.domain.user.entity.User;

public interface CommentService {

    CommentResponseDto createComment(CommentRequestDto commentRequestDto, User user, Long cardId);
}
