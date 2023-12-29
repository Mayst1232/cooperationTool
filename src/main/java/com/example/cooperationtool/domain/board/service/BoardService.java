package com.example.cooperationtool.domain.board.service;

import static com.example.cooperationtool.global.exception.ErrorCode.ILLEGAL_BOARD_TYPE;
import static com.example.cooperationtool.global.exception.ErrorCode.NOT_FOUND_BOARD;
import static com.example.cooperationtool.global.exception.ErrorCode.NOT_MATCH_USER;

import com.example.cooperationtool.domain.board.dto.BoardViewResponseDto;
import com.example.cooperationtool.domain.board.dto.request.BoardRequestDto;
import com.example.cooperationtool.domain.board.dto.response.BoardResponseDto;
import com.example.cooperationtool.domain.board.entity.Board;
import com.example.cooperationtool.domain.board.repository.BoardRepository;
import com.example.cooperationtool.domain.user.entity.User;
import com.example.cooperationtool.global.exception.ServiceException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
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
        if (!user.getId().equals(board.getUser().getId())) {
            throw new ServiceException(NOT_MATCH_USER);
        }
        boardRepository.delete(board);
    }

    @Transactional
    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto boardRequestDto, User user) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new ServiceException(NOT_FOUND_BOARD));
        if (!user.getId().equals(board.getUser().getId())) {
            throw new ServiceException(NOT_MATCH_USER);
        }
        board.update(boardRequestDto);
        return new BoardResponseDto(board);
    }

    public List<BoardViewResponseDto> getBoards(String type) {
        List<Board> boardList;
        if (type.equals("all")) {
            boardList = boardRepository.findAll();
        } else if (type.equals("recent")) {
            boardList = boardRepository.findAllByOrderByCreatedAtDesc();
        } else {
            throw new ServiceException(ILLEGAL_BOARD_TYPE);
        }
        return findAllByBoard(boardList);
    }

    private List<BoardViewResponseDto> findAllByBoard(List<Board> boards) {
        List<BoardViewResponseDto> boardViewResponseDto = new ArrayList<>();
        for (Board board : boards) {
            boardViewResponseDto
                .add(BoardViewResponseDto.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .nickname(board.getUser().getNickname())
                    .createdAt(board.getCreatedAt())
                    .build());
        }
        return boardViewResponseDto;
    }
}
