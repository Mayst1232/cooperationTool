package com.example.cooperationtool.domain.comment.service.Impl;

import com.example.cooperationtool.domain.card.repository.CardRepository;
import com.example.cooperationtool.domain.comment.dto.request.CommentRequestDto;
import com.example.cooperationtool.domain.comment.dto.response.CommentResponseDto;
import com.example.cooperationtool.domain.comment.entity.Comment;
import com.example.cooperationtool.domain.comment.repository.CommentRepository;
import com.example.cooperationtool.domain.comment.service.CommentService;
import com.example.cooperationtool.domain.user.entity.User;
import com.example.cooperationtool.global.exception.ErrorCode;
import com.example.cooperationtool.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;

    @Override
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, User user, Long cardId) {

        if (commentRequestDto.getContent() == null) {
            throw new ServiceException(ErrorCode.NOT_COMMENT_CONTENT);
        }

        Comment comment = Comment.builder()
            .content(commentRequestDto.getContent())
            .user(user)
            .build();

        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }
}
