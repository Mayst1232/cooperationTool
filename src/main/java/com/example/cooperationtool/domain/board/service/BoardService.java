package com.example.cooperationtool.domain.board.service;

import static com.example.cooperationtool.global.exception.ErrorCode.NOT_FOUND_BOARD;
import static com.example.cooperationtool.global.exception.ErrorCode.NOT_MATCH_USER;

import com.example.cooperationtool.domain.board.dto.request.BoardRequestDto;
import com.example.cooperationtool.domain.board.dto.response.BoardResponseDto;
import com.example.cooperationtool.domain.board.entity.Board;
import com.example.cooperationtool.domain.board.repository.BoardRepository;
import com.example.cooperationtool.domain.user.entity.User;
import com.example.cooperationtool.global.exception.ServiceException;
import java.util.Optional;
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

    public void deleteBoard(Long boardId, User user) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new ServiceException(NOT_FOUND_BOARD));
        if (!user.equals(board.getUser())) {
            throw new ServiceException(NOT_MATCH_USER);
        }

        boardRepository.delete(board);
    }

    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto boardRequestDto, User user) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new ServiceException(NOT_FOUND_BOARD));
        if (!user.equals(board.getUser())) {
            throw new ServiceException(NOT_MATCH_USER);
        }
        board.update(boardRequestDto);
        return new BoardResponseDto(board);
    }
}
