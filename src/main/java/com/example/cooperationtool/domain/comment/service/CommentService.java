package com.example.cooperationtool.domain.comment.service;

import com.example.cooperationtool.domain.comment.dto.request.CommentRequestDto;
import com.example.cooperationtool.domain.comment.dto.response.CommentResponseDto;
import com.example.cooperationtool.domain.user.entity.User;
import java.util.List;

public interface CommentService {

    CommentResponseDto createComment(CommentRequestDto commentRequestDto, User user, Long cardId);

    CommentResponseDto updateComment(CommentRequestDto commentRequestDto, User user,
        Long commentId);

    void deleteComment(User user, Long commentId);

    List<CommentResponseDto> getComment(Long cardId);
}
