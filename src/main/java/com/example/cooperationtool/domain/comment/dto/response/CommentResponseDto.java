package com.example.cooperationtool.domain.comment.dto.response;

import com.example.cooperationtool.domain.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

    @Getter
    public class CommentResponseDto{

        private final String content;
        private final String name;
        private final LocalDateTime createdAt;
        private final LocalDateTime modifiedAt;

        @Builder
        public CommentResponseDto(Comment comment) {
            this.content = comment.getContent();
            this.name = comment.getUser().getNickname();
            this.createdAt = comment.getCreatedAt();
            this.modifiedAt = comment.getModifiedAt();
        }
    }
