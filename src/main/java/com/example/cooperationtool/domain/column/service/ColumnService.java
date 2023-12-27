package com.example.cooperationtool.domain.column.service;

import com.example.cooperationtool.domain.column.dto.ColumnRequestDto;
import com.example.cooperationtool.domain.column.dto.ColumnResponseDto;
import com.example.cooperationtool.domain.column.entity.Column;
import com.example.cooperationtool.domain.column.repository.ColumnRepository;
import com.example.cooperationtool.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ColumnService {

    private final ColumnRepository columnRepository;

    public ColumnResponseDto createColumn(ColumnRequestDto columnRequestDto, User user){
        Column column = Column.builder()
            .name(columnRequestDto.getName())
            .user(user)
            .build();
        columnRepository.save(column);
        return new ColumnResponseDto(column);
    }
    public void moveColumn(Long columnId, boolean moveUp) {
        Column column = columnRepository.findById(columnId)
            .orElseThrow(() -> new IllegalArgumentException("해당하는 컬럼을 찾을 수 없습니다"));

        List<Column> columns = columnRepository.findByBoardIdOrderByOrderAsc(column.getBoard().getId());

        int currentIndex = columns.indexOf(column);

        if (currentIndex == -1) {
            throw new IllegalArgumentException("컬럼이 현재 보드에 속해있지 않습니다");
        }

        int newIndex = moveUp ? Math.max(0, currentIndex - 1) : Math.min(columns.size() - 1, currentIndex + 1);

        columns.remove(column);
        columns.add(newIndex, column);

        updateColumnOrder(columns);
    }

    private void updateColumnOrder(List<Column> columns) {
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).setOrder(i);
        }
        columnRepository.saveAll(columns);
    }
}
