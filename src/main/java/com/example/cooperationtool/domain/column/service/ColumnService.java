package com.example.cooperationtool.domain.column.service;

import com.example.cooperationtool.domain.column.dto.ColumnRequestDto;
import com.example.cooperationtool.domain.column.dto.ColumnResponseDto;
import com.example.cooperationtool.domain.column.entity.Columns;
import com.example.cooperationtool.domain.column.repository.ColumnRepository;
import com.example.cooperationtool.domain.user.entity.User;
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
                .orElseThrow(() -> new IllegalArgumentException("해당하는 컬럼을 찾을 수 없습니다"));

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
            .orElseThrow(() -> new IllegalArgumentException("해당하는 컬럼을 찾을 수 없습니다"));
        return new ColumnResponseDto(columns);
    }

    public void sortColumn(Long columnId, boolean sortUp) {
        Columns column = columnRepository.findById(columnId)
            .orElseThrow(() -> new IllegalArgumentException("해당하는 컬럼을 찾을 수 없습니다"));

        List<Columns> columns = columnRepository.findByBoardIdOrderByOrderAsc(column.getBoard().getId());

        int currentIndex = columns.indexOf(column);

        if (currentIndex == -1) {
            throw new IllegalArgumentException("컬럼이 현재 보드에 속해있지 않습니다");
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
            .orElseThrow(() -> new IllegalArgumentException("해당하는 컬럼을 찾을 수 없습니다"));
        if (!user.equals(columns.getUser())) {
            throw new RuntimeException("사용자가 일치하지 않음");
        }

        columnRepository.delete(columns);
    }
}
