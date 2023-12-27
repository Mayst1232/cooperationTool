package com.example.cooperationtool.domain.board.service;

import com.example.cooperationtool.domain.board.dto.request.BoardRequestDto;
import com.example.cooperationtool.domain.board.dto.response.BoardResponseDto;
import com.example.cooperationtool.domain.board.entity.Board;
import com.example.cooperationtool.domain.board.repository.BoardRepository;
import com.example.cooperationtool.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, User user) {
        Board board = Board.builder()
            .title(boardRequestDto.getTitle())
            .content(boardRequestDto.getContent())
            .user(user)
            .build();
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }

}
