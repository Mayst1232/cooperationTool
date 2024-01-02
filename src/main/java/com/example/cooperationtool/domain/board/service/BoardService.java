package com.example.cooperationtool.domain.board.service;

import static com.example.cooperationtool.global.exception.ErrorCode.ALREADY_INVITE_USER;
import static com.example.cooperationtool.global.exception.ErrorCode.ILLEGAL_BOARD_TYPE;
import static com.example.cooperationtool.global.exception.ErrorCode.NOT_EXIST_USER;
import static com.example.cooperationtool.global.exception.ErrorCode.NOT_FOUND_BOARD;
import static com.example.cooperationtool.global.exception.ErrorCode.NOT_INVITE_MYSELF;
import static com.example.cooperationtool.global.exception.ErrorCode.NOT_INVITE_USER;
import static com.example.cooperationtool.global.exception.ErrorCode.NOT_INVITE_YOURSELF;
import static com.example.cooperationtool.global.exception.ErrorCode.NOT_MATCH_BOARD;
import static com.example.cooperationtool.global.exception.ErrorCode.NOT_MATCH_USER;

import com.example.cooperationtool.domain.board.dto.request.BoardRequestDto;
import com.example.cooperationtool.domain.board.dto.response.BoardResponseDto;
import com.example.cooperationtool.domain.board.dto.response.BoardViewResponseDto;
import com.example.cooperationtool.domain.board.entity.Board;
import com.example.cooperationtool.domain.board.entity.InviteBoard;
import com.example.cooperationtool.domain.board.repository.BoardRepository;
import com.example.cooperationtool.domain.board.repository.InviteBoardRepository;
import com.example.cooperationtool.domain.user.entity.User;
import com.example.cooperationtool.domain.user.repository.UserRepository;
import com.example.cooperationtool.global.exception.ServiceException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final InviteBoardRepository inviteBoardRepository;

    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, User user) {
        Board board = Board.builder()
            .title(boardRequestDto.getTitle())
            .content(boardRequestDto.getContent())
            .user(user)
            .build();
        InviteBoard inviteBoard = InviteBoard.builder()
            .board(board)
            .user(user)
            .build();
        boardRepository.save(board);
        inviteBoardRepository.save(inviteBoard);
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

    public List<BoardViewResponseDto> getBoards(User user, String type) {
        List<Board> boardList = new ArrayList<>();
        List<InviteBoard> inviteBoardList;
        if (type.equals("all")) {
            inviteBoardList = inviteBoardRepository.findAllByUser_Id(user.getId());
            for (InviteBoard inviteBoard : inviteBoardList) {
                boardRepository.findById(inviteBoard.getBoard().getId()).ifPresent(boardList::add);
            }
        } else if (type.equals("recent")) {
            inviteBoardList = inviteBoardRepository.findAllByUser_Id(user.getId());
            for (InviteBoard inviteBoard : inviteBoardList) {
                boardRepository.findById(inviteBoard.getBoard().getId()).ifPresent(boardList::add);
            }
            boardList.sort(Comparator.comparing(Board::getCreatedAt).reversed());
        } else {
            throw new ServiceException(ILLEGAL_BOARD_TYPE);
        }
        return findAllByBoard(boardList);
    }

    public void inviteBoard(Long boardId, Long userId, User user) {
        Board checkBoard = boardRepository.findById(boardId)
            .orElseThrow(() -> new ServiceException(NOT_FOUND_BOARD));
        if (!checkBoard.getUser().getId().equals(user.getId())) {
            throw new ServiceException(NOT_MATCH_BOARD);
        }
        User checkUser = userRepository.findById(userId)
            .orElseThrow(() -> new ServiceException(NOT_EXIST_USER));
        if (checkUser.getId().equals(user.getId())) {
            throw new ServiceException(NOT_INVITE_YOURSELF);
        }
        if (inviteBoardRepository.findByUserAndBoard(checkUser, checkBoard).isPresent()) {
            throw new ServiceException(ALREADY_INVITE_USER);
        }
        InviteBoard inviteBoard = InviteBoard.builder()
            .user(checkUser)
            .board(checkBoard)
            .build();
        inviteBoardRepository.save(inviteBoard);
    }

    public void unInviteBoard(Long boardId, Long userId, User user) {
        Board checkBoard = boardRepository.findById(boardId)
            .orElseThrow(() -> new ServiceException(NOT_FOUND_BOARD));

        if (!checkBoard.getUser().getId().equals(user.getId())) {
            throw new ServiceException(NOT_MATCH_BOARD);
        }
        User checkUser = userRepository.findById(userId)
            .orElseThrow(() -> new ServiceException(NOT_EXIST_USER));
        if (checkUser.getId().equals(user.getId())) {
            throw new ServiceException(NOT_INVITE_MYSELF);
        }
        Optional<InviteBoard> inviteBoardOptional =
            inviteBoardRepository.findByUserAndBoard(checkUser, checkBoard);
        if (inviteBoardOptional.isPresent()) {
            InviteBoard inviteBoard = inviteBoardOptional.get();
            inviteBoardRepository.delete(inviteBoard);
        } else {
            throw new ServiceException(NOT_INVITE_USER);
        }
    }

    private List<BoardViewResponseDto> findAllByBoard(List<Board> boards) {
        List<BoardViewResponseDto> boardViewResponseDto = new ArrayList<>();
        for (Board board : boards) {
            boardViewResponseDto
                .add(BoardViewResponseDto.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .writer(board.getUser().getNickname())
                    .createdAt(board.getCreatedAt())
                    .modifiedAt(board.getModifiedAt())
                    .build());
        }
        return boardViewResponseDto;
    }
}
