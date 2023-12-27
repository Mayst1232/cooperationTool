package com.example.cooperationtool.domain.board.controller;


import static org.assertj.core.api.Assertions.assertThat;

import com.example.cooperationtool.domain.board.dto.request.BoardRequestDto;
import com.example.cooperationtool.domain.board.dto.response.BoardResponseDto;
import com.example.cooperationtool.domain.board.repository.BoardRepository;
import com.example.cooperationtool.domain.board.service.BoardService;
import com.example.cooperationtool.domain.user.entity.User;
import com.example.cooperationtool.domain.user.entity.UserRoleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    BoardRepository boardRepository;
    @InjectMocks
    BoardService boardService;

    @Test
    @DisplayName("보드 생성")
    public void createBoard() {

        // given
        User user = User.builder()
            .username("TestUser")
            .password("1224")
            .nickname("TestNickname")
            .introduce("TestMyself")
            .role(UserRoleEnum.USER)
            .build();
        BoardRequestDto requestDto = BoardRequestDto.builder()
            .title("TestTitle")
            .content("TestContent")
            .build();

        // when
        BoardResponseDto responseDto = boardService.createBoard(requestDto, user);

        // then
        assertThat(responseDto).isNotNull();
    }

}