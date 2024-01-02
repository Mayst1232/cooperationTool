package com.example.cooperationtool.domain.comment.service;

import com.example.cooperationtool.domain.card.dto.CardRequestDto;
import com.example.cooperationtool.domain.card.repository.CardRepository;
import com.example.cooperationtool.domain.comment.dto.request.CommentRequestDto;
import com.example.cooperationtool.domain.comment.dto.response.CommentResponseDto;
import com.example.cooperationtool.domain.comment.entity.Comment;
import com.example.cooperationtool.domain.comment.repository.CommentRepository;
import com.example.cooperationtool.domain.comment.service.Impl.CommentServiceImpl;
import com.example.cooperationtool.domain.user.entity.User;
import com.example.cooperationtool.domain.user.entity.UserRoleEnum;
import com.example.cooperationtool.domain.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    CardRepository cardRepository;
    @InjectMocks
    CommentServiceImpl commentServiceImpl;

    @Test
    @DisplayName("댓글 생성")
    void createComment() throws NotFoundException {
        // given
        User user = User.builder()
            .username("testUser")
            .password("1234")
            .nickname("testNickname")
            .introduce("testMyself")
            .role(UserRoleEnum.USER)
            .build();

        CardRequestDto cardRequestDto = CardRequestDto.builder()
            .title("testTitle")
            .build();

        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
            .content("testContent")
            .build();

        // Comment 객체를 Mock으로 생성
        Comment comment = Comment.builder()
            .content(commentRequestDto.getContent())
            .user(user)
            .build();

        // CommentRepository.save() 메서드를 호출하면서 Mock이 정의된 동작 설정
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // when
        CommentResponseDto responseDto = commentServiceImpl.createComment(commentRequestDto, user, comment.getId());

        // then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getContent()).isEqualTo(commentRequestDto.getContent());
    }
}
