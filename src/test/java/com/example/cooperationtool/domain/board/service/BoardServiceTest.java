package com.example.cooperationtool.domain.board.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.cooperationtool.domain.board.dto.request.BoardRequestDto;
import com.example.cooperationtool.domain.board.dto.response.BoardResponseDto;
import com.example.cooperationtool.domain.board.entity.Board;
import com.example.cooperationtool.domain.board.repository.BoardRepository;
import com.example.cooperationtool.domain.board.service.BoardService;
import com.example.cooperationtool.domain.user.entity.User;
import com.example.cooperationtool.domain.user.entity.UserRoleEnum;
import com.example.cooperationtool.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

//    @Mock
//    BoardRepository boardRepository;
//    @Mock
//    UserRepository userRepository;
//    @InjectMocks
//    BoardService boardService;
//
//    private User user1;
//    private Board board1;
//    private User user;
//    private Board board;
//
//    @BeforeEach
//    public void setup() {
//        user = User.builder()
//            .username("TestUser")
//            .password("1224")
//            .nickname("TestNickname")
//            .introduce("TestMyself")
//            .role(UserRoleEnum.USER)
//            .build();
//        user1 = userRepository.save(user);
//
//        board = Board.builder()
//            .title("TestTitle")
//            .content("TestContent")
//            .user(user)
//            .build();
//        board1 = boardRepository.save(board);
//    }

    @Test
    @DisplayName("보드 생성")
    public void createBoard() {

//        // given
//        User user = User.builder()
//            .username("TestUser")
//            .password("1224")
//            .nickname("TestNickname")
//            .introduce("TestMyself")
//            .role(UserRoleEnum.USER)
//            .build();
//
//        BoardRequestDto requestDto = BoardRequestDto.builder()
//            .title("TestTitle")
//            .content("TestContent")
//            .build();
//
//        // when
//        BoardResponseDto responseDto = boardService.createBoard(requestDto, user);
//
//        // then
//        assertThat(responseDto).isNotNull();
    }

    @Test
    @DisplayName("보드 삭제")
    public void deleteBoard() {

//        // given
//        Long boardId = 1L;
//        given(boardRepository.findById(boardId)).willReturn(Optional.of(board));
//
//        // when
//        boardService.deleteBoard(boardId, user);
//
//        // then
//        verify(boardRepository, times(1)).delete(any());
    }

}