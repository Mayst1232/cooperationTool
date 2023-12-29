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
import jakarta.transaction.Transactional;
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

    @Transactional
    @Override
    public CommentResponseDto updateComment(CommentRequestDto commentRequestDto, User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
            new ServiceException(ErrorCode.NOT_FOUND_COMMENT));

        if (!user.getId().equals(comment.getUser().getId())) {
            throw new ServiceException(ErrorCode.NOT_MATCH_USER);
        }
        comment.modifyContent(commentRequestDto.getContent());
        return new CommentResponseDto(comment);
    }
}
