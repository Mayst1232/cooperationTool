package com.example.cooperationtool.domain.column.service;

import static com.example.cooperationtool.global.exception.ErrorCode.NOT_FOUND_COLUMN;
import static com.example.cooperationtool.global.exception.ErrorCode.NOT_IN_COLUMN;
import static com.example.cooperationtool.global.exception.ErrorCode.NOT_MATCH_USER;
import com.example.cooperationtool.domain.column.dto.ColumnRequestDto;
import com.example.cooperationtool.domain.column.dto.ColumnResponseDto;
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

    public ColumnResponseDto createColumn(ColumnRequestDto columnRequestDto, User user){
        Columns columns = Columns.builder()
            .name(columnRequestDto.getName())
            .user(user)
            .build();
        columnRepository.save(columns);
        return new ColumnResponseDto(columns);
    }

        public void updateColumnName(Long columnId, String newName) {
            Columns columns = columnRepository.findById(columnId)
                .orElseThrow(() -> new ServiceException(NOT_FOUND_COLUMN));

            columns.setName(newName);
            columnRepository.save(columns);
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

    public void sortColumn(Long columnId, boolean sortUp) {
        Columns column = columnRepository.findById(columnId)
            .orElseThrow(() -> new ServiceException(NOT_FOUND_COLUMN));

        List<Columns> columns = columnRepository.findByBoardIdOrderByOrderAsc(column.getBoard().getId());

        int currentIndex = columns.indexOf(column);

        if (currentIndex == -1) {
            throw new ServiceException(NOT_IN_COLUMN);
        }

        int newIndex = sortUp ? Math.max(0, currentIndex - 1) : Math.min(columns.size() - 1, currentIndex + 1);

        columns.remove(column);
        columns.add(newIndex, column);

        updateColumnOrder(columns);
    }

    private void updateColumnOrder(List<Columns> columns) {
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).setSort(i);
        }
        columnRepository.saveAll(columns);
    }

    public void deleteColumn(Long columnId, User user) {
        Columns columns = columnRepository.findById(columnId)
            .orElseThrow(() -> new ServiceException(NOT_FOUND_COLUMN));
        if (!user.equals(columns.getUser())) {
            throw new ServiceException(NOT_MATCH_USER);
        }

        columnRepository.delete(columns);
    }
}
