package com.example.cooperationtool.domain.column.service;

import static com.example.cooperationtool.global.exception.ErrorCode.NOT_FOUND_BOARD;
import static com.example.cooperationtool.global.exception.ErrorCode.NOT_FOUND_COLUMN;
import static com.example.cooperationtool.global.exception.ErrorCode.NOT_INVITE_USER;
import static com.example.cooperationtool.global.exception.ErrorCode.NOT_MATCH_USER;

import com.example.cooperationtool.domain.board.entity.Board;
import com.example.cooperationtool.domain.board.entity.InviteBoard;
import com.example.cooperationtool.domain.board.repository.BoardRepository;
import com.example.cooperationtool.domain.board.repository.InviteBoardRepository;
import com.example.cooperationtool.domain.column.dto.ColumnRequestDto;
import com.example.cooperationtool.domain.column.dto.ColumnResponseDto;
import com.example.cooperationtool.domain.column.dto.MoveColumnRequestDto;
import com.example.cooperationtool.domain.column.entity.Columns;
import com.example.cooperationtool.domain.column.repository.ColumnRepository;
import com.example.cooperationtool.domain.user.entity.User;
import com.example.cooperationtool.global.exception.ServiceException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ColumnService {

    private final ColumnRepository columnRepository;
    private final InviteBoardRepository inviteBoardRepository;
    private final BoardRepository boardRepository;

    public ColumnResponseDto createColumn(ColumnRequestDto columnRequestDto, User user) {
        Long boardId = columnRequestDto.getBoardId();
        String title = columnRequestDto.getTitle();

        Board board = boardRepository.findById(boardId).orElseThrow(
            () -> new ServiceException(NOT_FOUND_BOARD)
        );

        Long priority = columnRepository.countByBoardId(boardId) + 1;

        Columns columns = Columns.builder()
            .title(title)
            .priority(priority)
            .board(board)
            .user(user)
            .build();

        Columns savedColumns = columnRepository.save(columns);

        return ColumnResponseDto.builder()
            .columns(savedColumns)
            .build();
    }

    public ColumnResponseDto updateColumnName(Long columnId, String title) {
        Columns columns = columnRepository.findById(columnId)
            .orElseThrow(() -> new ServiceException(NOT_FOUND_COLUMN));

        columns.setTitle(title);
        columnRepository.save(columns);

        return ColumnResponseDto.builder()
            .columns(columns)
            .build();
    }

    public List<ColumnResponseDto> getAllColumns() {
        List<Columns> columns = columnRepository.findAll();
        return columns.stream()
            .map(ColumnResponseDto::new)
            .collect(Collectors.toList());
    }

    public ColumnResponseDto getColumnById(Long columnId) {
        Columns columns = columnRepository.findById(columnId)
            .orElseThrow(() -> new ServiceException(NOT_FOUND_COLUMN));
        return new ColumnResponseDto(columns);
    }

    public void deleteColumn(Long columnId, User user) {

        Columns columns = columnRepository.findById(columnId)
            .orElseThrow(() -> new ServiceException(NOT_FOUND_COLUMN));

        if (!user.getUsername().equals(columns.getUser().getUsername())) {
            throw new ServiceException(NOT_MATCH_USER);
        }

        columnRepository.delete(columns);
    }

//    public void sortColumn(Long columnId, boolean sortUp) {
//        Columns column = columnRepository.findById(columnId)
//            .orElseThrow(() -> new ServiceException(NOT_FOUND_COLUMN));
//
//        List<Columns> columns = columnRepository.findByBoardIdOrderByPriorityAsc(
//            column.getBoard().getId());
//
//        int currentIndex = columns.indexOf(column);
//
//        if (currentIndex == -1) {
//            throw new ServiceException(NOT_IN_COLUMN);
//        }
//
//        int newIndex =
//            sortUp ? Math.max(0, currentIndex - 1) : Math.min(columns.size() - 1, currentIndex + 1);
//
//        columns.remove(column);
//        columns.add(newIndex, column);
//
//        updateColumnOrder(columns);
//    }
//
//    private void updateColumnOrder(List<Columns> columns) {
//        for (int i = 0; i < columns.size(); i++) {
//            columns.get(i).setPriority((long) i);
//        }
//        columnRepository.saveAll(columns);
//    }

    @Transactional
    public List<ColumnResponseDto> moveColumn(Long columnId, MoveColumnRequestDto requestDto,
        User user) {
        Columns findColumn = columnRepository.findById(columnId).orElseThrow(
            () -> new ServiceException(NOT_FOUND_COLUMN)
        );
        InviteBoard checkInvite = inviteBoardRepository.findByUserAndBoard(user,
            findColumn.getBoard()).orElseThrow(
            () -> new ServiceException(NOT_INVITE_USER)
        );

        Long priority = findColumn.getPriority();
        Long wantPriority = requestDto.getDestination();
        Long moveDirection;
        Long start;
        Long end;

        if (priority > wantPriority) {
            moveDirection = 1L;
            start = wantPriority;
            end = priority;
        } else if (priority < wantPriority) {
            moveDirection = -1L;
            start = priority;
            end = wantPriority;
        } else {
            moveDirection = 0L;
            start = priority;
            end = priority;
        }

        columnRepository.moveColumn(start, end, moveDirection);

        findColumn.move(wantPriority);

        List<Columns> columnsList = columnRepository.findAllOrderByPriority(
            findColumn.getBoard().getId());

        return columnsList.stream().map(ColumnResponseDto::new).collect(Collectors.toList());
    }
}
